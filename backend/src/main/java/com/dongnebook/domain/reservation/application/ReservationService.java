package com.dongnebook.domain.reservation.application;

import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dongnebook.domain.book.application.port.out.BookRepositoryPort;
import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.book.domain.BookState;
import com.dongnebook.domain.book.application.BookNotFoundException;
import com.dongnebook.domain.member.application.MemberService;
import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.rental.domain.Rental;
import com.dongnebook.domain.rental.domain.RentalState;
import com.dongnebook.domain.rental.repository.RentalQueryRepository;
import com.dongnebook.domain.reservation.domain.Reservation;
import com.dongnebook.domain.reservation.dto.response.ReservationInfoResponse;
import com.dongnebook.domain.reservation.exception.CanNotChangeReservationStateException;
import com.dongnebook.domain.reservation.exception.CanNotReservationBookRentalStateException;
import com.dongnebook.domain.reservation.exception.CanNotReservationPersonException;
import com.dongnebook.domain.reservation.exception.ReservationNotFoundException;
import com.dongnebook.domain.reservation.repository.ReservationQueryRepository;
import com.dongnebook.domain.reservation.repository.ReservationRepository;
import com.dongnebook.global.dto.request.PageRequestImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationQueryRepository reservationQueryRepository;
    private final BookRepositoryPort bookRepositoryPort;
    private final MemberService memberService;
    private final RentalQueryRepository rentalQueryRepository;

    @Transactional
    public void createReservation(Long bookId, Long memberId){
        Book book = getBookById(bookId);
        checkBookState(book, BookState.UNRENTABLE_RESERVABLE);
        Rental rental = getRentalByBookId(bookId);
        checkRentalState(rental, RentalState.BEING_RENTED);
        Member customer = memberService.getById(memberId);
        checkReservationPerson(memberId, book, rental, customer);

        book.changeBookStateFromTo(BookState.UNRENTABLE_RESERVABLE, BookState.UNRENTABLE_UNRESERVABLE);

        Reservation reservation = Reservation.create(rental, customer, book);
        reservationRepository.save(reservation);
    }

    public Boolean checkReservationAvailability(Long bookId, Long memberId){
        Book book = getBookById(bookId);
        Rental rental = getRentalByBookId(bookId);
        Member customer = memberService.getById(memberId);
        try {
            checkReservationPerson(memberId, book, rental, customer);
            return true;
        } catch (CanNotReservationPersonException e) {
            return false;
        }
    }

    public SliceImpl<ReservationInfoResponse> readReservations(Long memberId, PageRequestImpl pageRequestImpl){
        return reservationQueryRepository.findAllByMemberIdOrderByIdDesc(memberId, pageRequestImpl);
    }

    @Transactional
    public void cancelReservation(Long reservationId, Long memberId){
        Reservation reservation = getReservation(reservationId);
        checkCancelReservationPerson(reservation, memberId);

        Book book = reservation.getBook();
        book.changeBookStateFromTo(BookState.UNRENTABLE_UNRESERVABLE, BookState.UNRENTABLE_RESERVABLE);

        reservationRepository.delete(reservation);
    }

    private Book getBookById(Long bookId) {
        return bookRepositoryPort.findById(bookId).orElseThrow(BookNotFoundException::new);
    }

    private Rental getRentalByBookId(Long bookId) {
        return rentalQueryRepository.getRentalByBookId(bookId).get(0);
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

    private static void checkCancelReservationPerson(Reservation reservation, Long memberId) {
        if(!reservation.getMember().getId().equals(memberId)){
            throw new CanNotChangeReservationStateException();
        }
    }

}
