package com.dongnebook.domain.rental.application;

import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.book.domain.BookState;
import com.dongnebook.domain.book.exception.BookNotFoundException;
import com.dongnebook.domain.book.exception.CanNotChangeBookStateException;
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

        canChangeBookState(book, BookState.RENTABLE, BookState.TRADING);

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

        canChangeRentalState(rental, RentalState.TRADING, RentalState.CANCELED);
        rental.setCanceledAt(LocalDateTime.now());
        canChangeBookState(book, BookState.TRADING, BookState.RENTABLE);
    }

    @Transactional
    public void receiveBook(Long rentalId){
        Rental rental = getRental(rentalId);
        Book book = getBookFromRental(rental);

        // 해당 주민만 수령 가능
//        if(!rental.getMember().getId().equals("receive하는 추제"))
//            throw new CanNotReceiveRentalException();

        canChangeRentalState(rental, RentalState.TRADING, RentalState.BEING_RENTED);
        canChangeBookState(book, BookState.TRADING, BookState.UNRENTABLE_RESERVABLE);
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
        canChangeRentalState(rental, RentalState.BEING_RENTED, RentalState.RETURN_UNREVIEWED);
        canChangeBookState(book, BookState.UNRENTABLE_RESERVABLE, BookState.RENTABLE);
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

    public void canChangeBookState(Book book, BookState from, BookState to) {
        if(!book.getBookState().equals(from)){
            throw new CanNotChangeBookStateException();
        } else {
            book.changeBookState(to);
        }
    }

    public void canChangeRentalState(Rental rental, RentalState from, RentalState to){
        if(!rental.getRentalState().equals(from)){
            throw new CanNotChangeRentalStateException();
        } else {
            rental.changeRentalState(to);
        }
    }

}
