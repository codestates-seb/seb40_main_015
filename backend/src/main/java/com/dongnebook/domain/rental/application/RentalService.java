package com.dongnebook.domain.rental.application;

import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.book.domain.BookState;
import com.dongnebook.domain.book.exception.BookNotFoundException;
import com.dongnebook.domain.book.repository.BookCommandRepository;
import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.member.exception.MemberNotFoundException;
import com.dongnebook.domain.member.repository.MemberRepository;
import com.dongnebook.domain.rental.domain.Rental;

import com.dongnebook.domain.rental.domain.RentalState;

import com.dongnebook.domain.rental.dto.Request.RentalRegisterRequest;
import com.dongnebook.domain.rental.exception.*;

import com.dongnebook.domain.rental.repository.RentalRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RentalService {

	private final RentalRepository rentalRepository;
	private final BookCommandRepository bookCommandRepository;
	private final MemberRepository memberRepository;

	@Transactional
	public Long createRental(RentalRegisterRequest rentalRegisterRequest) {
		Member member = getMemberById(rentalRegisterRequest.getMemberId());
		Book book = getBookById(rentalRegisterRequest.getBookId());

		blockRentMyBook(rentalRegisterRequest, book);
		book.changeBookStateFromTo(BookState.RENTABLE, BookState.TRADING);

		Rental rental = Rental.create(book, member);
		rentalRepository.save(rental);

		return rental.getId();
	}

	// 추후 cancel 추체에 따른 알림 대상이 달라지기에 cancel 주체별로 메서드 분리가 필요함
	@Transactional
	public void cancelRental(Long rentalId) {
		Rental rental = getRental(rentalId);
		Book book = getBookFromRental(rental);

		//        // 해당 주민이 취소하는 경우
		//        if(!rental.getMember().getId().equals("cancel하는 주체"))
		//            throw new CanNotCancelRentalException();
		//        // 해당 상인이 취소하는 경우
		//        if(!book.getMember().getId().equals("cancel하는 주체"))
		//            throw new CanNotCancelRentalException();

		rental.changeRentalStateFromTo(RentalState.TRADING, RentalState.CANCELED);
		book.changeBookStateFromTo(BookState.TRADING, BookState.RENTABLE);
	}

	@Transactional
	public void receiveBook(Long rentalId) {
		Rental rental = getRental(rentalId);
		Book book = getBookFromRental(rental);

		// 해당 주민만 수령 가능
		//        if(!rental.getMember().getId().equals("receive하는 추제"))
		//            throw new CanNotReceiveRentalException();

		rental.changeRentalStateFromTo(RentalState.TRADING, RentalState.BEING_RENTED);
		book.changeBookStateFromTo(BookState.TRADING, BookState.UNRENTABLE_RESERVABLE);
	}

	// 예약이 없을 경우에 대한 반납 case (예약이 있을 경우에 대한 반납 case 또한 만들어줘야 함)
	@Transactional
	public void returnRental(Long rentalId){
		Rental rental = getRental(rentalId);
		Book book = getBookFromRental(rental);

		// 해당 상인만 반납 가능
//        if(!book.getMember().getId().equals("cancel하는 주체"))
//            throw new CanNotReturnRentalException();

		//예약이 없을 경우
		rental.changeRentalStateFromTo(RentalState.BEING_RENTED, RentalState.RETURN_UNREVIEWED);
		book.changeBookStateFromTo(BookState.UNRENTABLE_RESERVABLE, BookState.RENTABLE);
	}


	private Book getBookById(Long rentalRegisterRequest) {
		return bookCommandRepository.findById(rentalRegisterRequest)
			.orElseThrow(BookNotFoundException::new);
	}

	private Member getMemberById(Long id) {
		return memberRepository.findById(id)
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
	private void blockRentMyBook(RentalRegisterRequest rentalRegisterRequest, Book book) {
		if (rentalRegisterRequest.getMemberId().equals(book.getMember().getId())) {
			throw new CanNotRentMyBookException();
		}
	}
}
