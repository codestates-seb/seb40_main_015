package com.dongnebook.domain.rental.application;

import com.dongnebook.domain.alarm.application.AlarmService;
import com.dongnebook.domain.book.application.BookQueryService;
import com.dongnebook.domain.book.application.port.in.response.BookInfoResponse;
import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.book.domain.BookProduct;
import com.dongnebook.domain.book.domain.BookState;
import com.dongnebook.domain.book.domain.Money;
import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.member.exception.MemberNotFoundException;
import com.dongnebook.domain.member.repository.MemberRepository;
import com.dongnebook.domain.model.Location;
import com.dongnebook.domain.rental.domain.Rental;
import com.dongnebook.domain.rental.domain.RentalState;
import com.dongnebook.domain.rental.dto.request.RentalSearchCondition;
import com.dongnebook.domain.rental.dto.response.RentalBookResponse;
import com.dongnebook.domain.rental.dto.response.RentalInfoResponse;
import com.dongnebook.domain.rental.exception.CanNotRentMyBookException;
import com.dongnebook.domain.rental.exception.RentalNotFoundException;
import com.dongnebook.domain.rental.repository.RentalQueryRepository;
import com.dongnebook.domain.rental.repository.RentalRepository;
import com.dongnebook.domain.reservation.domain.Reservation;
import com.dongnebook.domain.reservation.repository.ReservationQueryRepository;
import com.dongnebook.domain.reservation.repository.ReservationRepository;
import com.dongnebook.global.dto.request.PageRequestImpl;
import com.dongnebook.global.error.exception.CanNotChangeStateException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.SliceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RentalServiceTest2 {
    @Mock
    private RentalRepository rentalRepository;
    @Mock
    private RentalQueryRepository rentalQueryRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private ReservationQueryRepository reservationQueryRepository;
    @Mock
    private AlarmService alarmService;
    @Mock
    private BookQueryService bookQueryService;
    @InjectMocks
    private RentalService rentalService;

    @Test
    public void getMemberByIdTest() throws Exception {
        // given
        Long bookId = 1L;
        Long customerId = 1L;
        Optional<Member> optional = Optional.empty();

        when(memberRepository.findById(Mockito.any(Long.class))).thenReturn(optional);

        // when & then
        assertThrows(MemberNotFoundException.class, () -> rentalService.createRental(bookId, customerId));
    }

    @Test
    public void blockRentMyBookTest() throws Exception {
        // given
        Long bookId = 1L;
        Long customerId = 1L;
        Member member = new Member(1L, "test", "tester", new Location(37.5340, 126.7064), "anywhere", "abc@abc.com");
        Optional<Member> optionalMember = Optional.of(member);

        when(memberRepository.findById(customerId)).thenReturn(optionalMember);

        Book book = new Book(1L, new BookProduct(), "aaa@abc.com", "기본이 중요하다", Money.of(1000), new Location(37.5340, 126.7064), member);
        when(bookQueryService.getWithMerchantByBookId(bookId)).thenReturn(book);

        // when & then
        assertThrows(CanNotRentMyBookException.class, () -> rentalService.createRental(bookId, customerId));
    }

    @Test
    public void createRentalTest() throws Exception {
        // given
        Long bookId = 1L;
        Long customerId = 1L;
        Member member1 = new Member(customerId, "test1", "tester1", new Location(37.5340, 126.7064), "anywhere", "abc1@abc.com");
        Optional<Member> optionalMember1 = Optional.of(member1);

        when(memberRepository.findById(customerId)).thenReturn(optionalMember1);

        Long merchantId = 2L;
        Member member2 = new Member(merchantId, "test2", "tester2", new Location(37.5340, 126.7064), "anywhere", "abc2@abc.com");
        Book book = new Book(1L, new BookProduct(), "aaa@abc.com", "기본이 중요하다", Money.of(1000), new Location(37.5340, 126.7064), member2);
        when(bookQueryService.getWithMerchantByBookId(bookId)).thenReturn(book);

//        doNothing().when(alarmService).sendAlarm(Mockito.any(Member.class), Mockito.any(Book.class), Mockito.any(AlarmType.class));

        // when & then
        assertDoesNotThrow(() -> rentalService.createRental(bookId, customerId));
    }

    @Test
    public void getRentalTest() throws Exception {
        // given
        Long rentalId = 1L;
        Long customerId = 1L;
        Optional<Rental> optional = Optional.empty();

        given(rentalRepository.findById(rentalId)).willReturn(optional);

        // when & then
        assertThrows(RentalNotFoundException.class, () -> rentalService.cancelRentalByCustomer(rentalId, customerId));
    }

    @Test
    public void CanNotChangeRentalStateExceptionTest() throws Exception {
        // given
        Long rentalId = 1L;
        Long customerId = 2L;

        Member merchant = new Member(1L, "test1", "tester1", new Location(37.5340, 126.7064), "anywhere", "abc1@abc.com");
        Book book = new Book(1L, new BookProduct(), "aaa@abc.com", "기본이 중요하다", Money.of(1000), new Location(37.5340, 126.7064), merchant);

        Member customer = new Member(customerId, "test2", "tester2", new Location(37.5340, 126.7064), "anywhere", "abc2@abc.com");
        Rental rental = new Rental(LocalDateTime.now(), RentalState.BEING_RENTED, merchant.getId(), book, customer);
        Optional<Rental> optionalRental = Optional.of(rental);
        given(rentalRepository.findById(rentalId)).willReturn(optionalRental);

        given(bookQueryService.getByBookId(rental.getBook().getId())).willReturn(book);

        // when & then
        assertThrows(CanNotChangeStateException.class, () -> rentalService.cancelRentalByCustomer(rentalId, customerId));
    }

    @Test
    public void CanNotChangeBookStateException() throws Exception {
        // given
        Long rentalId = 1L;
        Long customerId = 2L;

        Member merchant = new Member(1L, "test1", "tester1", new Location(37.5340, 126.7064), "anywhere", "abc1@abc.com");
        Book book = new Book(1L, new BookProduct(), "aaa@abc.com", "기본이 중요하다", Money.of(1000), new Location(37.5340, 126.7064), merchant);

        Member customer = new Member(customerId, "test2", "tester2", new Location(37.5340, 126.7064), "anywhere", "abc2@abc.com");
        Rental rental = new Rental(LocalDateTime.now(), RentalState.TRADING, merchant.getId(), book, customer);
        Optional<Rental> optionalRental = Optional.of(rental);
        given(rentalRepository.findById(rentalId)).willReturn(optionalRental);

        given(bookQueryService.getByBookId(rental.getBook().getId())).willReturn(book);

        // when & then
        assertThrows(CanNotChangeStateException.class, () -> rentalService.cancelRentalByCustomer(rentalId, customerId));
    }

    @Test
    public void cancelRentalByCustomerTest() throws Exception {
        // given
        Long rentalId = 1L;
        Long customerId = 2L;

        Member merchant = new Member(1L, "test1", "tester1", new Location(37.5340, 126.7064), "anywhere", "abc1@abc.com");
        Book book = new Book(1L, new BookProduct(), "aaa@abc.com", "기본이 중요하다", Money.of(1000), new Location(37.5340, 126.7064), merchant);

        Member customer = new Member(customerId, "test2", "tester2", new Location(37.5340, 126.7064), "anywhere", "abc2@abc.com");
        Rental rental = new Rental(LocalDateTime.now(), RentalState.TRADING, merchant.getId(), book, customer);
        Optional<Rental> optionalRental = Optional.of(rental);
        given(rentalRepository.findById(rentalId)).willReturn(optionalRental);

        given(bookQueryService.getByBookId(rental.getBook().getId())).willReturn(book);
        book.changeBookStateFromTo(BookState.RENTABLE, BookState.TRADING);

        // when & then
        assertDoesNotThrow(() -> rentalService.cancelRentalByCustomer(rentalId, customerId));
    }

    @Test
    public void CanNotChangeStateExceptionTest() throws Exception {
        //given
        Long rentalId = 1L;
        Long merchantId = 1L;

        Member merchant = new Member(merchantId, "test1", "tester1", new Location(37.5340, 126.7064), "anywhere", "abc1@abc.com");
        Member otherMerchant = new Member(3L, "test3", "tester3", new Location(37.5340, 126.7064), "anywhere", "abc3@abc.com");
        Book book = new Book(1L, new BookProduct(), "aaa@abc.com", "기본이 중요하다", Money.of(1000), new Location(37.5340, 126.7064), otherMerchant);

        Member customer = new Member(2L, "test2", "tester2", new Location(37.5340, 126.7064), "anywhere", "abc2@abc.com");
        Rental rental = new Rental(LocalDateTime.now(), RentalState.TRADING, merchant.getId(), book, customer);
        Optional<Rental> optionalRental = Optional.of(rental);
        given(rentalRepository.findById(rentalId)).willReturn(optionalRental);

        given(bookQueryService.getByBookId(rental.getBook().getId())).willReturn(book);
        book.changeBookStateFromTo(BookState.RENTABLE, BookState.TRADING);

        // when & then
        assertThrows(CanNotChangeStateException.class, () -> rentalService.cancelRentalByMerchant(rentalId, merchantId));
    }

    @Test
    public void cancelRentalByMerchantTest() throws Exception {
        //given
        Long rentalId = 1L;
        Long merchantId = 1L;

        Member merchant = new Member(merchantId, "test1", "tester1", new Location(37.5340, 126.7064), "anywhere", "abc1@abc.com");
        Book book = new Book(1L, new BookProduct(), "aaa@abc.com", "기본이 중요하다", Money.of(1000), new Location(37.5340, 126.7064), merchant);

        Member customer = new Member(2L, "test2", "tester2", new Location(37.5340, 126.7064), "anywhere", "abc2@abc.com");
        Rental rental = new Rental(LocalDateTime.now(), RentalState.TRADING, merchant.getId(), book, customer);
        Optional<Rental> optionalRental = Optional.of(rental);
        given(rentalRepository.findById(rentalId)).willReturn(optionalRental);

        given(bookQueryService.getByBookId(rental.getBook().getId())).willReturn(book);
        book.changeBookStateFromTo(BookState.RENTABLE, BookState.TRADING);

        // when & then
        assertDoesNotThrow(() -> rentalService.cancelRentalByMerchant(rentalId, merchantId));
    }

    @Test
    public void receiveBookTest() throws Exception {
        // given
        Long rentalId = 1L;
        Long customerId = 1L;

        Member merchant = new Member(1L, "test1", "tester1", new Location(37.5340, 126.7064), "anywhere", "abc1@abc.com");
        Book book = new Book(1L, new BookProduct(), "aaa@abc.com", "기본이 중요하다", Money.of(1000), new Location(37.5340, 126.7064), merchant);

        Member customer = new Member(customerId, "test2", "tester2", new Location(37.5340, 126.7064), "anywhere", "abc2@abc.com");
        Rental rental = new Rental(LocalDateTime.now(), RentalState.TRADING, merchant.getId(), book, customer);
        Optional<Rental> optionalRental = Optional.of(rental);
        given(rentalRepository.findById(rentalId)).willReturn(optionalRental);

        given(bookQueryService.getByBookId(rental.getBook().getId())).willReturn(book);
        book.changeBookStateFromTo(BookState.RENTABLE, BookState.TRADING);

        // when & then
        assertDoesNotThrow(() -> rentalService.receiveBook(rentalId, customerId));
    }

    @Test
    public void returnRentalTest1() throws Exception {
        // given
        Long rentalId = 1L;
        Long merchantId = 1L;

        Member merchant = new Member(merchantId, "test1", "tester1", new Location(37.5340, 126.7064), "anywhere", "abc1@abc.com");
        Book book = new Book(1L, new BookProduct(), "aaa@abc.com", "기본이 중요하다", Money.of(1000), new Location(37.5340, 126.7064), merchant);

        Member customer = new Member(2L, "test2", "tester2", new Location(37.5340, 126.7064), "anywhere", "abc2@abc.com");
        Rental rental = new Rental(LocalDateTime.now(), RentalState.TRADING, merchant.getId(), book, customer);
        Optional<Rental> optionalRental = Optional.of(rental);
        given(rentalRepository.findById(rentalId)).willReturn(optionalRental);

        given(bookQueryService.getByBookId(rental.getBook().getId())).willReturn(book);

        rental.changeRentalStateFromTo(RentalState.TRADING, RentalState.BEING_RENTED);

        book.changeBookStateFromTo(BookState.RENTABLE, BookState.UNRENTABLE_RESERVABLE);

        // when & then
        assertDoesNotThrow(() -> rentalService.returnRental(rentalId, merchantId));
    }

    @Test
    public void returnRentalTest2() throws Exception {
        // given
        Long rentalId = 1L;
        Long merchantId = 1L;

        Member merchant = new Member(merchantId, "test1", "tester1", new Location(37.5340, 126.7064), "anywhere", "abc1@abc.com");
        Book book = new Book(1L, new BookProduct(), "aaa@abc.com", "기본이 중요하다", Money.of(1000), new Location(37.5340, 126.7064), merchant);

        Member customer = new Member(2L, "test2", "tester2", new Location(37.5340, 126.7064), "anywhere", "abc2@abc.com");
        LocalDateTime localDateTime = LocalDateTime.now();
        Rental rental = new Rental(localDateTime, RentalState.TRADING, merchant.getId(), book, customer);
        Optional<Rental> optionalRental = Optional.of(rental);
        given(rentalRepository.findById(rentalId)).willReturn(optionalRental);

        given(bookQueryService.getByBookId(rental.getBook().getId())).willReturn(book);

        rental.changeRentalStateFromTo(RentalState.TRADING, RentalState.BEING_RENTED);

        book.changeBookStateFromTo(BookState.RENTABLE, BookState.UNRENTABLE_UNRESERVABLE);

        Reservation reservation = new Reservation(1L, localDateTime.plusDays(9), localDateTime.plusDays(9),  rental, customer, book);
        given(reservationQueryRepository.getReservationByRentalId(rental.getId())).willReturn(List.of(reservation));

        doNothing().when(reservationRepository).delete(reservation);

        // when & then
        assertDoesNotThrow(() -> rentalService.returnRental(rentalId, merchantId));
    }

    @Test
    public void getRentalsByMerchantTest() throws Exception{
        // given
        Long merchantId = 1L;
        RentalSearchCondition rentalState = new RentalSearchCondition((RentalState.TRADING).toString());
        String stringRentalState = rentalState.toString();
        PageRequestImpl pageRequestImpl = new PageRequestImpl(2L);

        LocalDateTime rentalStartedAt = LocalDateTime.now();
        LocalDateTime rentalDeadline = rentalStartedAt.plusDays(9);

        BookInfoResponse bookInfoResponse1 =
                new BookInfoResponse(
                        1L, "IMG_1.jpg", "자바의 정석1","남궁성", "도우출판", 1500,
                        "apple is delicious", new Location(37.5340, 126.7064), BookState.TRADING, "jujang", 1L
                );
        BookInfoResponse bookInfoResponse2 =
                new BookInfoResponse(
                        2L, "IMG_2.jpg", "자바의 정석2","남궁성", "도우출판", 1500,
                        "banana is cool", new Location(37.5340, 126.7064), BookState.TRADING, "jujang", 1L
                );
        RentalInfoResponse rentalInfoResponse1 =
                new RentalInfoResponse(
                        8L, "jj", RentalState.TRADING, rentalStartedAt, rentalDeadline, null, null
                );
        RentalInfoResponse rentalInfoResponse2 =
                new RentalInfoResponse(
                        9L, "jj", RentalState.TRADING, rentalStartedAt, rentalDeadline, null, null
                );

        SliceImpl<RentalBookResponse> rentalBookResponseSlice =
                new SliceImpl<>(
                        List.of(
                                new RentalBookResponse(bookInfoResponse1, rentalInfoResponse1),
                                new RentalBookResponse(bookInfoResponse2, rentalInfoResponse2)
                        )
                );

        given(rentalQueryRepository.findAllByMerchantIdOrderByIdDesc(merchantId, stringRentalState, pageRequestImpl))
                .willReturn(rentalBookResponseSlice);

        // when & then
        assertDoesNotThrow(() -> rentalService.getRentalsByMerchant(merchantId, stringRentalState, pageRequestImpl));
    }

    @Test
    public void getRentalsByCustomerTest() throws Exception{
        // given
        Long customerId = 2L;
        RentalSearchCondition rentalState = new RentalSearchCondition((RentalState.TRADING).toString());
        String stringRentalState = rentalState.toString();
        PageRequestImpl pageRequestImpl = new PageRequestImpl(2L);

        LocalDateTime rentalStartedAt = LocalDateTime.now();
        LocalDateTime rentalDeadline = rentalStartedAt.plusDays(9);

        BookInfoResponse bookInfoResponse1 =
                new BookInfoResponse(
                        1L, "IMG_1.jpg", "자바의 정석1","남궁성", "도우출판", 1500,
                        "apple is delicious", new Location(37.5340, 126.7064), BookState.TRADING, "jujang", 1L
                );
        BookInfoResponse bookInfoResponse2 =
                new BookInfoResponse(
                        2L, "IMG_2.jpg", "자바의 정석2","남궁성", "도우출판", 1500,
                        "banana is cool", new Location(37.5340, 126.7064), BookState.TRADING, "jujang", 1L
                );
        RentalInfoResponse rentalInfoResponse1 =
                new RentalInfoResponse(
                        8L, "jj", RentalState.TRADING, rentalStartedAt, rentalDeadline, null, null
                );
        RentalInfoResponse rentalInfoResponse2 =
                new RentalInfoResponse(
                        9L, "jj", RentalState.TRADING, rentalStartedAt, rentalDeadline, null, null
                );

        SliceImpl<RentalBookResponse> rentalBookResponseSlice =
                new SliceImpl<>(
                        List.of(
                                new RentalBookResponse(bookInfoResponse1, rentalInfoResponse1),
                                new RentalBookResponse(bookInfoResponse2, rentalInfoResponse2)
                        )
                );

        given(rentalQueryRepository.findAllByCustomerIdOrderByIdDesc(customerId, stringRentalState, pageRequestImpl))
                .willReturn(rentalBookResponseSlice);

        // when & then
        assertDoesNotThrow(() -> rentalService.getRentalsByCustomer(customerId, stringRentalState, pageRequestImpl));
    }

    @Test
    public void oneDayBeforeReturnTest() throws Exception{
        // given
        LocalDateTime localDateTime = LocalDateTime.now();
        Long merchantId = 1L;
        Member merchant = new Member(merchantId, "test1", "tester1", new Location(37.5340, 126.7064), "anywhere", "abc1@abc.com");
        Book book = new Book(1L, new BookProduct(), "aaa@abc.com", "기본이 중요하다", Money.of(1000), new Location(37.5340, 126.7064), merchant);
        Member customer = new Member(2L, "test2", "tester2", new Location(37.5340, 126.7064), "anywhere", "abc2@abc.com");

        Rental rental = new Rental(localDateTime.plusDays(9), RentalState.BEING_RENTED, merchantId, book, customer);
        List<Rental> list = List.of(rental);

        given(rentalQueryRepository.findAllDeadLineRental(Mockito.any(LocalDate.class))).willReturn(list);

        // when & then
        assertDoesNotThrow(() -> rentalService.oneDayBeforeReturn());
    }
}
