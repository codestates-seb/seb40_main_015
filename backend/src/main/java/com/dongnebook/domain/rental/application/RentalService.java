package com.dongnebook.domain.rental.application;

import com.dongnebook.domain.alarm.domain.AlarmService;
import com.dongnebook.domain.alarm.domain.AlarmType;
import com.dongnebook.domain.book.application.BookService;
import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.book.domain.BookState;
import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.member.exception.MemberNotFoundException;
import com.dongnebook.domain.member.repository.MemberRepository;
import com.dongnebook.domain.rental.domain.Rental;

import com.dongnebook.domain.rental.domain.RentalState;

import com.dongnebook.domain.rental.dto.response.RentalBookResponse;
import com.dongnebook.domain.rental.exception.*;

import com.dongnebook.domain.rental.repository.RentalQueryRepository;
import com.dongnebook.domain.rental.repository.RentalRepository;

import com.dongnebook.domain.reservation.domain.Reservation;
import com.dongnebook.domain.reservation.repository.ReservationQueryRepository;
import com.dongnebook.domain.reservation.repository.ReservationRepository;
import com.dongnebook.global.dto.request.PageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import javax.persistence.OptimisticLockException;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RentalService {

	private final RentalRepository rentalRepository;
	private final AlarmService alarmService;
	private final RentalQueryRepository rentalQueryRepository;
	private final BookService bookService;
	private final MemberRepository memberRepository;
	private final ReservationQueryRepository reservationQueryRepository;
	private final ReservationRepository reservationRepository;
	private final ApplicationEventPublisher applicationEventPublisher;

	@Transactional
	public void createRental(Long bookId, Long customerId) {
		Member customer = getMemberById(customerId);
		Book book =  bookService.getByBookIdWithMerchant(bookId);
		blockRentMyBook(customerId, book);

			book.changeBookStateFromTo(BookState.RENTABLE, BookState.TRADING);

		Rental rental = Rental.create(book, customer);
		applicationEventPublisher.publishEvent(new RentalCreateEvent(rental));

		alarmService.sendAlarm(book.getMember(), book, AlarmType.RENTAL);
	}

	// 해당 주민이 취소하는 경우
	@Transactional
	public void cancelRentalByCustomer(Long rentalId, Long customerId) {
		Rental rental = getRental(rentalId);
		Book book = getBookFromRental(rental);

		// 책을 빌린 주민 본인이 아닌 경우 예외 처리
		canNotChangeRental(rental.getCustomer(), customerId);

		rental.changeRentalStateFromTo(RentalState.TRADING, RentalState.CANCELED);
		book.changeBookStateFromTo(BookState.TRADING, BookState.RENTABLE);
		rental.setCanceledAt(LocalDateTime.now());

		alarmService.sendAlarm(book.getMember(),book, AlarmType.RESIDENT_CANCELLATION);
	}

	// 해당 상인이 취소하는 경우
	@Transactional
	public void cancelRentalByMerchant(Long rentalId, Long merchantId) {
		Rental rental = getRental(rentalId);
		Book book = getBookFromRental(rental);

		// 대여를 올린 상인 본인이 아닌 경우 예외 처리
		canNotChangeRental(book.getMember(), merchantId);

		rental.changeRentalStateFromTo(RentalState.TRADING, RentalState.CANCELED);
		book.changeBookStateFromTo(BookState.TRADING, BookState.RENTABLE);
		rental.setCanceledAt(LocalDateTime.now());

		alarmService.sendAlarm(rental.getCustomer(),book, AlarmType.MERCHANT_CANCELLATION);
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

	@Transactional
	public void returnRental(Long rentalId, Long merchantId){
		Rental rental = getRental(rentalId);
		Book book = getBookFromRental(rental);

		// 대여를 올린 상인 본인이 아닌 경우 예외 처리
		canNotChangeRental(book.getMember(), merchantId);

		rental.changeRentalStateFromTo(RentalState.BEING_RENTED, RentalState.RETURN_UNREVIEWED);
		rental.setReturnedAt(LocalDateTime.now());

		// 예약이 없을 경우
		if(book.getBookState().equals(BookState.UNRENTABLE_RESERVABLE)){
			book.changeBookStateFromTo(BookState.UNRENTABLE_RESERVABLE, BookState.RENTABLE);
		} else { // 예약이 있을 경우 - 반납 즉시, 예약은 대여로 전환(=대여 등록)
			book.changeBookStateFromTo(BookState.UNRENTABLE_UNRESERVABLE, BookState.TRADING);

			Reservation reservation = reservationQueryRepository.getReservationByRentalId(rental.getId()).get(0);
			reservationRepository.delete(reservation);

			Rental newRental = Rental.create(book, reservation.getMember());
			rentalRepository.save(newRental);
		}
	}

	public SliceImpl<RentalBookResponse> getRentalsByMerchant(Long merchantId, String rentalState, PageRequest pageRequest) {
		return rentalQueryRepository.findAllByMerchantIdOrderByIdDesc(merchantId, rentalState, pageRequest);
	}

	public SliceImpl<RentalBookResponse> getRentalsByCustomer(Long customerId, String rentalState, PageRequest pageRequest) {
		return rentalQueryRepository.findAllByCustomerIdOrderByIdDesc(customerId, rentalState, pageRequest);
	}


	private Member getMemberById(Long memberId) {
		log.info("memberId={}",memberId);
		return memberRepository.findById(memberId)
			.orElseThrow(MemberNotFoundException::new);
	}

	private Rental getRental(Long rentalId) {
		return rentalRepository.findById(rentalId)
			.orElseThrow(RentalNotFoundException::new);
	}

	private Book getBookFromRental(Rental rental) {
		return bookService.getByBookId(rental.getBook().getId());
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
