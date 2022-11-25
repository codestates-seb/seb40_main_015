package com.dongnebook.domain.reservation.application;

import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.book.domain.BookState;
import com.dongnebook.domain.book.exception.BookNotFoundException;
import com.dongnebook.domain.book.repository.BookCommandRepository;
import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.member.exception.MemberNotFoundException;
import com.dongnebook.domain.member.repository.MemberRepository;
import com.dongnebook.domain.rental.domain.Rental;
import com.dongnebook.domain.rental.domain.RentalState;
import com.dongnebook.domain.rental.repository.RentalQueryRepository;
import com.dongnebook.domain.rental.repository.RentalRepository;
import com.dongnebook.domain.reservation.domain.Reservation;
import com.dongnebook.domain.reservation.domain.ReservationState;
import com.dongnebook.domain.reservation.exception.CanNotChangeReservationStateException;
import com.dongnebook.domain.reservation.exception.CanNotReservationBookRentalStateException;
import com.dongnebook.domain.reservation.exception.CanNotReservationPersonException;
import com.dongnebook.domain.reservation.exception.ReservationNotFoundException;
import com.dongnebook.domain.reservation.repository.ReservationQueryRepository;

import com.dongnebook.domain.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationQueryRepository reservationQueryRepository;
    private final BookCommandRepository bookCommandRepository;
    private final MemberRepository memberRepository;
    private final RentalQueryRepository rentalQueryRepository;
    private final RentalRepository rentalRepository;


    @Transactional
    public void createReservation(Long bookId, Long memberId){
        Book book = getBookById(bookId);
        checkBookState(book, BookState.UNRENTABLE_RESERVABLE);
        Rental rental = getRentalByBookId(bookId);
        checkRentalState(rental, RentalState.BEING_RENTED);
        Member customer = getMemberById(memberId, book, rental);
        checkReservationPerson(memberId, book, rental, customer);

        book.changeBookStateFromTo(BookState.UNRENTABLE_RESERVABLE, BookState.UNRENTABLE_UNRESERVABLE);

        Reservation reservation = Reservation.create(rental, customer, book);
        reservationRepository.save(reservation);
    }

    @Transactional
    public void cancelReservation(Long reservationId, Long memberId){
        Reservation reservation = getReservation(reservationId);
        checkCancelReservationState(reservation, ReservationState.ON_RESERVATION);
        checkCancelReservationPerson(reservation, memberId);

        Book book = reservation.getBook();
        book.changeBookStateFromTo(BookState.UNRENTABLE_UNRESERVABLE, BookState.UNRENTABLE_RESERVABLE);

        reservationRepository.delete(reservation);
    }

    private Book getBookById(Long bookId) {
        return bookCommandRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
    }

    private Member getMemberById(Long memberId, Book book, Rental rental) {
        return memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
    }

    private Rental getRentalByBookId(Long bookId) {
        return rentalQueryRepository.getRental(bookId).get(0);
    }

    private Reservation getReservation(Long reservationId) {
        Reservation reservation = reservationQueryRepository.getReservationById(reservationId);
        if(reservation == null) {
            throw new ReservationNotFoundException();
        }
        return reservation;
    }

    private static void checkBookState(Book book, BookState bookState) {
        if(!book.getBookState().equals(bookState)){
            throw new CanNotReservationBookRentalStateException();
        }
    }

    private static void checkRentalState(Rental rental, RentalState rentalState) {
        if(!rental.getRentalState().equals(rentalState)){
            throw new CanNotReservationBookRentalStateException();
        }
    }

    private static void checkReservationPerson(Long memberId, Book book, Rental rental, Member customer) {
        if(customer.getId().equals(book.getMember().getId())
                || rental.getCustomer().getId().equals(memberId)){
            throw new CanNotReservationPersonException();
        }
    }

    private static void checkCancelReservationState(Reservation reservation, ReservationState reservationState) {
        if(!reservation.getReservationState().equals(reservationState)){
            throw new CanNotChangeReservationStateException();
        }
    }

    private static void checkCancelReservationPerson(Reservation reservation, Long memberId) {
        if(!reservation.getMember().getId().equals(memberId)){
            throw new CanNotChangeReservationStateException();
        }
    }

}
