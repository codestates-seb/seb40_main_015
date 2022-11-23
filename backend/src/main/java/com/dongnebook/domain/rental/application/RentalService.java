package com.dongnebook.domain.rental.application;

import com.dongnebook.domain.alarm.domain.AlarmService;
import com.dongnebook.domain.alarm.domain.AlarmType;
import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.book.domain.BookState;
import com.dongnebook.domain.book.exception.BookNotFoundException;
import com.dongnebook.domain.book.repository.BookCommandRepository;
import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.member.exception.MemberNotFoundException;
import com.dongnebook.domain.member.repository.MemberRepository;
import com.dongnebook.domain.rental.domain.Rental;

import com.dongnebook.domain.rental.domain.RentalState;

import com.dongnebook.domain.rental.dto.Response.RentalBookResponse;
import com.dongnebook.domain.rental.exception.*;

import com.dongnebook.domain.rental.repository.RentalQueryRepository;
import com.dongnebook.domain.rental.repository.RentalRepository;

import com.dongnebook.global.dto.request.PageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RentalService {

	private final RentalRepository rentalRepository;
	private final AlarmService alarmService;
	private final RentalQueryRepository rentalQueryRepository;
	private final BookCommandRepository bookCommandRepository;
	private final MemberRepository memberRepository;

	@Transactional
	public void createRental(Long bookId, Long customerId) {
		Member merchant = getMemberById(customerId);
		Book book = getBookById(bookId);

		blockRentMyBook(customerId, book);
		book.changeBookStateFromTo(BookState.RENTABLE, BookState.TRADING);
		alarmService.sendAlarm(book.getMember(),book, AlarmType.RENTAL);
		Rental rental = Rental.create(book, merchant);
		rentalRepository.save(rental);
	}

	// 해당 주민이 취소하는 경우
	@Transactional
	public void cancelRentalByCustomer(Long rentalId, Long customerId) {
		Rental rental = getRental(rentalId);
		Book book = getBookFromRental(rental);
		alarmService.sendAlarm(book.getMember(),book, AlarmType.RESIDENT_CANCELLATION);
		// 책을 빌린 주민 본인이 아닌 경우 예외 처리
		canNotChangeRental(rental.getCustomer(), customerId);

		rental.changeRentalStateFromTo(RentalState.TRADING, RentalState.CANCELED);
		book.changeBookStateFromTo(BookState.TRADING, BookState.RENTABLE);
	}

	// 해당 상인이 취소하는 경우
	@Transactional
	public void cancelRentalByMerchant(Long rentalId, Long merchantId) {
		Rental rental = getRental(rentalId);
		Book book = getBookFromRental(rental);
		alarmService.sendAlarm(rental.getCustomer(),book, AlarmType.MERCHANT_CANCELLATION);
		// 대여를 올린 상인 본인이 아닌 경우 예외 처리
		canNotChangeRental(book.getMember(), merchantId);

		rental.changeRentalStateFromTo(RentalState.TRADING, RentalState.CANCELED);
		book.changeBookStateFromTo(BookState.TRADING, BookState.RENTABLE);
	}

	@Transactional
	public void receiveBook(Long rentalId, Long customerId) {
		Rental rental = getRental(rentalId);
		Book book = getBookFromRental(rental);

		// 책을 빌린 주민 본인이 아닌 경우 예외 처리
		canNotChangeRental(rental.getCustomer(), customerId);

		rental.changeRentalStateFromTo(RentalState.TRADING, RentalState.BEING_RENTED);
		book.changeBookStateFromTo(BookState.TRADING, BookState.UNRENTABLE_RESERVABLE);
	}

	// 예약이 없을 경우에 대한 반납 case (예약이 있을 경우에 대한 반납 case 또한 만들어줘야 함)
	@Transactional
	public void returnRental(Long rentalId, Long merchantId){
		Rental rental = getRental(rentalId);
		Book book = getBookFromRental(rental);

		// 대여를 올린 상인 본인이 아닌 경우 예외 처리
		canNotChangeRental(book.getMember(), merchantId);

		//예약이 없을 경우
		rental.changeRentalStateFromTo(RentalState.BEING_RENTED, RentalState.RETURN_UNREVIEWED);
		book.changeBookStateFromTo(BookState.UNRENTABLE_RESERVABLE, BookState.RENTABLE);
	}

	public SliceImpl<RentalBookResponse> getRentalsByMerchant(Long merchantId, PageRequest pageRequest) {
		return rentalQueryRepository.findAllByMerchantIdOrderByIdDesc(merchantId, pageRequest);
	}

	public SliceImpl<RentalBookResponse> getRentalsByCustomer(Long customerId, PageRequest pageRequest) {
		return rentalQueryRepository.findAllByCustomerIdOrderByIdDesc(customerId, pageRequest);
	}

	private Book getBookById(Long bookId) {
		return bookCommandRepository.findById(bookId)
			.orElseThrow(BookNotFoundException::new);
	}

	private Member getMemberById(Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(MemberNotFoundException::new);
	}

	private Rental getRental(Long rentalId) {
		return rentalRepository.findById(rentalId)
			.orElseThrow(RentalNotFoundException::new);
	}

	private Book getBookFromRental(Rental rental) {
		return getBookById(rental.getBook().getId());
	}

	// 상인이 본인 책을 대여하는 경우 예외 처리
	private void blockRentMyBook(Long memberId, Book book) {
		if (memberId.equals(book.getMember().getId())) {
			throw new CanNotRentMyBookException();
		}
	}

	public void canNotChangeRental(Member member, Long memberId){
		if(!member.getId().equals(memberId)){
			throw new CanNotChangeStateException();
		}
	}
}
