package com.dongnebook.domain.reservation.application;

import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.book.domain.BookState;
import com.dongnebook.domain.book.exception.BookNotFoundException;
import com.dongnebook.domain.book.repository.BookCommandRepository;
import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.member.exception.MemberNotFoundException;
import com.dongnebook.domain.member.repository.MemberRepository;
import com.dongnebook.domain.rental.domain.Rental;
import com.dongnebook.domain.rental.repository.RentalQueryRepository;
import com.dongnebook.domain.reservation.domain.Reservation;
import com.dongnebook.domain.reservation.exception.CanNotReservationBookStateException;
import com.dongnebook.domain.reservation.exception.CanNotReservationPersonException;
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
    private final BookCommandRepository bookCommandRepository;
    private final MemberRepository memberRepository;
    private final RentalQueryRepository rentalQueryRepository;

    @Transactional
    public void createReservation(Long bookId, Long memberId){
        Book book = getBookById(bookId);
        Rental rental = getRentalByBookId(bookId);
        Member customer = getMemberById(memberId, book, rental);

        book.changeBookStateFromTo(BookState.UNRENTABLE_RESERVABLE, BookState.UNRENTABLE_UNRESERVABLE);

        Reservation reservation = Reservation.create(rental, customer, book);
        reservationRepository.save(reservation);
    }

    private Book getBookById(Long bookId) {
        Book book = bookCommandRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        if(!book.getBookState().equals(BookState.UNRENTABLE_RESERVABLE)){
            throw new CanNotReservationBookStateException();
        }
        return book;
    }

    private Member getMemberById(Long memberId, Book book, Rental rental) {
        Member customer = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        if(customer.getId().equals(book.getMember().getId())
                || rental.getCustomer().getId().equals(memberId)){
            throw new CanNotReservationPersonException();
        }
        return customer;
    }

    private Rental getRentalByBookId(Long bookId) {
        return rentalQueryRepository.getRental(bookId).get(0);
    }

}
