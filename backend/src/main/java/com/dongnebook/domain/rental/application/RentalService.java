package com.dongnebook.domain.rental.application;

import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.book.domain.BookState;
import com.dongnebook.domain.book.exception.BookNotFoundException;
import com.dongnebook.domain.book.exception.NotRentableException;
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
        Member member =  memberRepository.findById(rentalRegisterRequest.getMemberId())
                .orElseThrow(MemberNotFoundException::new);

        Book book = bookCommandRepository.findById(rentalRegisterRequest.getBookId())
                .orElseThrow(BookNotFoundException::new);

        blockRentMyBook(rentalRegisterRequest, book);

        changeToTradingBookStateWhenRentableBookState(book);

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

        changeToCanceledRentalStateWhenTradingRentalState(rental);
        changeToRentableBookStateWhenTradingBookState(book);
    }

    @Transactional
    public void receiveBook(Long rentalId){
        Rental rental = getRental(rentalId);
        Book book = getBookFromRental(rental);

        // 해당 주민만 수령 가능
//        if(!rental.getMember().getId().equals("receive하는 추제"))
//            throw new CanNotReceiveRentalException();

        changeToBeingRentedRentalStateWhenTradingRentalState(rental);
        changeToUnrentalbeReservableBookStateWhenTradingBookState(book);
    }



    public Rental getRental(Long rentalId) {
        return rentalRepository.findById(rentalId)
                .orElseThrow(RentalNotFoundException::new);
    }

    public Book getBookFromRental(Rental rental) {
        return bookCommandRepository.findById(rental.getBook().getId())
                .orElseThrow(BookNotFoundException::new);
    }

    // 상인이 본인 책을 대여하는 경우 예외 처리
    private static void blockRentMyBook(RentalRegisterRequest rentalRegisterRequest, Book book) {
        if(rentalRegisterRequest.getMemberId().equals(book.getMember().getId())){
            throw new CanNotRentMyBookException();
        }
    }

    // 도서상태가 대여가능인 경우, 거래가능으로 변경
    private static void changeToTradingBookStateWhenRentableBookState(Book book) {
        if(!book.getBookState().equals(BookState.RENTABLE)) {
            throw new NotRentableException();
        } else {
            book.changeBookState(BookState.TRADING);
        }
    }

    // 대여상태가 거래중인 경우, 취소된 대여로 변경
    private static void changeToCanceledRentalStateWhenTradingRentalState(Rental rental) {
        if(!rental.getRentalState().equals(RentalState.TRADING)){
            throw new NotCancelableException();
        } else {
            rental.changeRentalState(RentalState.CANCELED);
            rental.setCanceledAt(LocalDateTime.now());
        }
    }

    // 도서상태가 거래중인 경우, 대여가능으로 변경
    private static void changeToRentableBookStateWhenTradingBookState(Book book) {
        if(!book.getBookState().equals(BookState.TRADING)) {
            throw new NotCancelableException();
        } else {
            book.changeBookState(BookState.RENTABLE);
        }
    }

    // 대여상태가 거래중인 경우, 대여중으로 변경
    private static void changeToBeingRentedRentalStateWhenTradingRentalState(Rental rental) {
        if(!rental.getRentalState().equals(RentalState.TRADING)) {
            throw new NotReceivableException();
        } else {
            rental.changeRentalState(RentalState.BEING_RENTED);
        }
    }

    // 도서상태가 거래중인 경우, 거래중&예약가능으로 변경
    private static void changeToUnrentalbeReservableBookStateWhenTradingBookState(Book book) {
        if(!book.getBookState().equals(BookState.TRADING)) {
            throw new NotReceivableException();
        } else {
            book.changeBookState(BookState.UNRENTABLE_RESERVABLE);
        }
    }

}
