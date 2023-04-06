package com.dongnebook.domain.reservation.service;

import com.dongnebook.domain.book.application.BookNotFoundException;
import com.dongnebook.domain.book.application.port.out.BookRepositoryPort;
import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.book.domain.BookProduct;
import com.dongnebook.domain.book.domain.BookState;
import com.dongnebook.domain.book.domain.Money;
import com.dongnebook.domain.member.application.MemberService;
import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.model.Location;
import com.dongnebook.domain.rental.domain.Rental;
import com.dongnebook.domain.rental.domain.RentalState;
import com.dongnebook.domain.rental.repository.RentalQueryRepository;
import com.dongnebook.domain.reservation.application.ReservationService;
import com.dongnebook.domain.reservation.domain.Reservation;
import com.dongnebook.domain.reservation.exception.CanNotChangeReservationStateException;
import com.dongnebook.domain.reservation.exception.CanNotReservationBookRentalStateException;
import com.dongnebook.domain.reservation.exception.CanNotReservationPersonException;
import com.dongnebook.domain.reservation.exception.ReservationNotFoundException;
import com.dongnebook.domain.reservation.repository.ReservationQueryRepository;
import com.dongnebook.domain.reservation.repository.ReservationRepository;
import com.dongnebook.global.dto.request.PageRequestImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private ReservationQueryRepository reservationQueryRepository;
    @Mock
    private BookRepositoryPort bookRepositoryPort;
    @Mock
    private MemberService memberService;
    @Mock
    private RentalQueryRepository rentalQueryRepository;
    @InjectMocks
    private ReservationService reservationService;

    @Test
    public void getBookByIdTest() {
        // given
        Long bookId = 1L;
        Long memberId = 1L;
        Optional<Book> optional = Optional.empty();

        when(bookRepositoryPort.findById(Mockito.any(Long.class))).thenReturn(optional);

        // when & then
        assertThrows(BookNotFoundException.class, () -> reservationService.createReservation(bookId, memberId));
    }

    @Test
    public void checkBookStateTest() {
        // given
        Long bookId = 1L;
        Long memberId = 1L;
        Member merchant = new Member(memberId, "test1", "tester1", new Location(37.5340, 126.7064), "anywhere", "abc1@abc.com");
        Book book = new Book(bookId, new BookProduct(), "aaa@abc.com", "기본이 중요하다", Money.of(1000), new Location(37.5340, 126.7064), merchant);

        Optional<Book> optionalBook = Optional.of(book);
        when(bookRepositoryPort.findById(Mockito.any(Long.class))).thenReturn(optionalBook);

        // when & then
        assertThrows(CanNotReservationBookRentalStateException.class, () -> reservationService.createReservation(bookId, memberId));
    }

    @Test
    public void checkRentalStateTest() {
        // given
        Long bookId = 1L;
        Long merchantId = 1L;
        Member merchant = new Member(merchantId, "test1", "tester1", new Location(37.5340, 126.7064), "anywhere", "abc1@abc.com");
        Book book = new Book(bookId, new BookProduct(), "aaa@abc.com", "기본이 중요하다", Money.of(1000), new Location(37.5340, 126.7064), merchant);
        book.changeBookStateFromTo(BookState.RENTABLE, BookState.UNRENTABLE_RESERVABLE);

        Optional<Book> optionalBook = Optional.of(book);
        when(bookRepositoryPort.findById(Mockito.any(Long.class))).thenReturn(optionalBook);

        Long customerId = 2L;
        Member customer = new Member(customerId, "test2", "tester2", new Location(37.5340, 126.7064), "anywhere", "abc2@abc.com");
        Rental rental = new Rental(LocalDateTime.now(), RentalState.TRADING, merchantId, book, customer);
        List<Rental> rentalList = List.of(rental);

        when(rentalQueryRepository.getRentalByBookId(Mockito.anyLong())).thenReturn(rentalList);

        // when & then
        assertThrows(CanNotReservationBookRentalStateException.class, () -> reservationService.createReservation(bookId, merchantId));
    }

    @Test
    public void checkReservationPersonTest1() {
        // given
        Long bookId = 1L;
        Long merchantId = 1L;
        Member merchant = new Member(merchantId, "test1", "tester1", new Location(37.5340, 126.7064), "anywhere", "abc1@abc.com");
        Book book = new Book(bookId, new BookProduct(), "aaa@abc.com", "기본이 중요하다", Money.of(1000), new Location(37.5340, 126.7064), merchant);
        book.changeBookStateFromTo(BookState.RENTABLE, BookState.UNRENTABLE_RESERVABLE);

        Optional<Book> optionalBook = Optional.of(book);
        when(bookRepositoryPort.findById(Mockito.any(Long.class))).thenReturn(optionalBook);

        Long customerId = 2L;
        Member customer = new Member(customerId, "test2", "tester2", new Location(37.5340, 126.7064), "anywhere", "abc2@abc.com");
        Rental rental = new Rental(LocalDateTime.now(), RentalState.BEING_RENTED, merchantId, book, merchant);
        List<Rental> rentalList = List.of(rental);
        when(rentalQueryRepository.getRentalByBookId(Mockito.anyLong())).thenReturn(rentalList);

        when(memberService.getById(merchantId)).thenReturn(customer);

        // when & then
        assertThrows(CanNotReservationPersonException.class, () -> reservationService.createReservation(bookId, merchantId));
    }

    @Test
    public void checkReservationPersonTest2() {
        // given
        Long bookId = 1L;
        Long merchantId = 1L;
        Member merchant = new Member(merchantId, "test1", "tester1", new Location(37.5340, 126.7064), "anywhere", "abc1@abc.com");
        Book book = new Book(bookId, new BookProduct(), "aaa@abc.com", "기본이 중요하다", Money.of(1000), new Location(37.5340, 126.7064), merchant);
        book.changeBookStateFromTo(BookState.RENTABLE, BookState.UNRENTABLE_RESERVABLE);

        Optional<Book> optionalBook = Optional.of(book);
        when(bookRepositoryPort.findById(Mockito.any(Long.class))).thenReturn(optionalBook);

        Long customerId = 2L;
        Member customer = new Member(customerId, "test2", "tester2", new Location(37.5340, 126.7064), "anywhere", "abc2@abc.com");
        Rental rental = new Rental(LocalDateTime.now(), RentalState.BEING_RENTED, merchantId, book, customer);
        List<Rental> rentalList = List.of(rental);
        when(rentalQueryRepository.getRentalByBookId(Mockito.anyLong())).thenReturn(rentalList);

        when(memberService.getById(merchantId)).thenReturn(merchant);

        // when & then
        assertThrows(CanNotReservationPersonException.class, () -> reservationService.createReservation(bookId, merchantId));
    }

    @Test
    public void createReservationTest() {
        // given
        Long bookId = 1L;
        Long merchantId = 1L;
        Member merchant = new Member(merchantId, "test1", "tester1", new Location(37.5340, 126.7064), "anywhere", "abc1@abc.com");
        Book book = new Book(bookId, new BookProduct(), "aaa@abc.com", "기본이 중요하다", Money.of(1000), new Location(37.5340, 126.7064), merchant);
        book.changeBookStateFromTo(BookState.RENTABLE, BookState.UNRENTABLE_RESERVABLE);

        Optional<Book> optionalBook = Optional.of(book);
        when(bookRepositoryPort.findById(Mockito.any(Long.class))).thenReturn(optionalBook);

        Long customerId = 2L;
        Member customer = new Member(customerId, "test2", "tester2", new Location(37.5340, 126.7064), "anywhere", "abc2@abc.com");
        Rental rental = new Rental(LocalDateTime.now(), RentalState.BEING_RENTED, merchantId, book, customer);
        List<Rental> rentalList = List.of(rental);
        when(rentalQueryRepository.getRentalByBookId(Mockito.anyLong())).thenReturn(rentalList);

        when(memberService.getById(merchantId)).thenReturn(customer);

        // when & then
        assertDoesNotThrow(() -> reservationService.createReservation(bookId, merchantId));
    }

    @Test
    public void readReservationsTest() {
        // given
        Long memberId = 1L;
        PageRequestImpl pageRequest = new PageRequestImpl(1L);

        // when & then
        assertDoesNotThrow(() -> reservationService.readReservations(memberId, pageRequest));
    }

    @Test
    public void getReservationTest() {
        // given
        Long reservationId = 1L;
        Long memberId = 1L;

        Reservation reservation = null;
        when(reservationQueryRepository.getReservationById(Mockito.anyLong())).thenReturn(reservation);

        // when & then
        assertThrows(ReservationNotFoundException.class, () -> reservationService.cancelReservation(reservationId, memberId));
    }

    @Test
    public void checkCancelReservationPersonTest() {
        // given
        Long reservationId = 1L;
        Long memberId = 3L;

        Long bookId = 1L;
        Long merchantId = 1L;
        Member merchant = new Member(merchantId, "test1", "tester1", new Location(37.5340, 126.7064), "anywhere", "abc1@abc.com");
        Book book = new Book(bookId, new BookProduct(), "aaa@abc.com", "기본이 중요하다", Money.of(1000), new Location(37.5340, 126.7064), merchant);

        Long customerId = 2L;
        Member customer = new Member(customerId, "test2", "tester2", new Location(37.5340, 126.7064), "anywhere", "abc2@abc.com");
        Rental rental = new Rental(LocalDateTime.now(), RentalState.BEING_RENTED, merchantId, book, customer);

        Member otherCustomer = new Member(memberId, "test3", "tester3", new Location(37.5340, 126.7064), "anywhere", "abc3@abc.com");
        Reservation reservation = new Reservation(reservationId, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(10), rental, customer, book);
        when(reservationQueryRepository.getReservationById(Mockito.anyLong())).thenReturn(reservation);

        // when & then
        assertThrows(CanNotChangeReservationStateException.class, () -> reservationService.cancelReservation(reservationId, memberId));
    }

    @Test
    public void cancelReservationTest() {
        // given
        Long reservationId = 1L;
        Long memberId = 3L;

        Long bookId = 1L;
        Long merchantId = 1L;
        Member merchant = new Member(merchantId, "test1", "tester1", new Location(37.5340, 126.7064), "anywhere", "abc1@abc.com");
        Book book = new Book(bookId, new BookProduct(), "aaa@abc.com", "기본이 중요하다", Money.of(1000), new Location(37.5340, 126.7064), merchant);

        Long customerId = 2L;
        Member customer = new Member(customerId, "test2", "tester2", new Location(37.5340, 126.7064), "anywhere", "abc2@abc.com");
        Rental rental = new Rental(LocalDateTime.now(), RentalState.BEING_RENTED, merchantId, book, customer);
        book.changeBookStateFromTo(BookState.RENTABLE, BookState.UNRENTABLE_UNRESERVABLE);

        Member otherCustomer = new Member(memberId, "test3", "tester3", new Location(37.5340, 126.7064), "anywhere", "abc3@abc.com");
        Reservation reservation = new Reservation(reservationId, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(10), rental, otherCustomer, book);
        when(reservationQueryRepository.getReservationById(Mockito.anyLong())).thenReturn(reservation);

        // when & then
        assertDoesNotThrow(() -> reservationService.cancelReservation(reservationId, memberId));
    }
}