package com.dongnebook.domain.reservation.repository;

import com.dongnebook.config.TestConfig;
import com.dongnebook.domain.book.adapter.out.BookCommandRepository;
import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.book.domain.BookProduct;
import com.dongnebook.domain.book.domain.Money;
import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.member.repository.MemberRepository;
import com.dongnebook.domain.model.Location;
import com.dongnebook.domain.rental.domain.Rental;
import com.dongnebook.domain.rental.domain.RentalState;
import com.dongnebook.domain.rental.repository.RentalRepository;
import com.dongnebook.domain.reservation.domain.Reservation;
import com.dongnebook.domain.reservation.dto.response.ReservationInfoResponse;
import com.dongnebook.global.dto.request.PageRequestImpl;
import com.dongnebook.support.DataClearExtension;
import com.dongnebook.support.DatabaseCleaner;
import com.github.gavlyukovskiy.boot.jdbc.decorator.DataSourceDecoratorAutoConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.SliceImpl;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ImportAutoConfiguration(DataSourceDecoratorAutoConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({TestConfig.class, DatabaseCleaner.class})
@ExtendWith(DataClearExtension.class)
public class ReservationQueryRepositoryTest {
    @Autowired
    private ReservationQueryRepository reservationQueryRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private BookCommandRepository bookCommandRepository;
    @Autowired
    private RentalRepository rentalRepository;
    @BeforeEach
    public void setting() {
        Member member1 = Member.builder()
                .userId("test")
                .avatarUrl("aaa@aa.com")
                .nickname("tester")
                .password("12341234")
                .build();
        Member savedMember = memberRepository.save(member1);

        BookProduct bookProduct = new BookProduct("수학의 정석1", "남궁성", "도우출판");
        Book book = Book.builder()
                .id(1L)
                .bookProduct(bookProduct)
                .imgUrl("aaa@abc.com")
                .description("기본이 중요하다")
                .rentalFee(Money.of(1000))
                .location(new Location(37.5340, 126.7064))
                .member(savedMember)
                .build();
        Book savedBook = bookCommandRepository.save(book);

        BookProduct bookProduct2 = new BookProduct("수학의 정석2", "남궁성", "도우출판");
        Book book2 = Book.builder()
                .id(2L)
                .bookProduct(bookProduct2)
                .imgUrl("aaa@abc.com")
                .description("기본이 중요하다")
                .rentalFee(Money.of(1000))
                .location(new Location(37.5340, 126.7064))
                .member(savedMember)
                .build();
        Book savedBook2 = bookCommandRepository.save(book2);

        BookProduct bookProduct3 = new BookProduct("수학의 정석3", "남궁성", "도우출판");
        Book book3 = Book.builder()
                .id(3L)
                .bookProduct(bookProduct3)
                .imgUrl("aaa@abc.com")
                .description("기본이 중요하다")
                .rentalFee(Money.of(1000))
                .location(new Location(37.5340, 126.7064))
                .member(savedMember)
                .build();
        Book savedBook3 = bookCommandRepository.save(book3);

        BookProduct bookProduct4 = new BookProduct("수학의 정석4", "남궁성", "도우출판");
        Book book4 = Book.builder()
                .id(4L)
                .bookProduct(bookProduct4)
                .imgUrl("aaa@abc.com")
                .description("기본이 중요하다")
                .rentalFee(Money.of(1000))
                .location(new Location(37.5340, 126.7064))
                .member(savedMember)
                .build();
        Book savedBook4 = bookCommandRepository.save(book4);

        BookProduct bookProduct5 = new BookProduct("수학의 정석5", "남궁성", "도우출판");
        Book book5 = Book.builder()
                .id(5L)
                .bookProduct(bookProduct5)
                .imgUrl("aaa@abc.com")
                .description("기본이 중요하다")
                .rentalFee(Money.of(1000))
                .location(new Location(37.5340, 126.7064))
                .member(savedMember)
                .build();
        Book savedBook5 = bookCommandRepository.save(book5);

        BookProduct bookProduct6 = new BookProduct("수학의 정석6", "남궁성", "도우출판");
        Book book6 = Book.builder()
                .id(6L)
                .bookProduct(bookProduct6)
                .imgUrl("aaa@abc.com")
                .description("기본이 중요하다")
                .rentalFee(Money.of(1000))
                .location(new Location(37.5340, 126.7064))
                .member(savedMember)
                .build();
        Book savedBook6 = bookCommandRepository.save(book6);

        BookProduct bookProduct7 = new BookProduct("수학의 정석7", "남궁성", "도우출판");
        Book book7 = Book.builder()
                .id(7L)
                .bookProduct(bookProduct7)
                .imgUrl("aaa@abc.com")
                .description("기본이 중요하다")
                .rentalFee(Money.of(1000))
                .location(new Location(37.5340, 126.7064))
                .member(savedMember)
                .build();
        Book savedBook7 = bookCommandRepository.save(book7);

        Member member2 = Member.builder()
                .userId("test2")
                .avatarUrl("aaa@aa.com")
                .nickname("tester2")
                .password("12341234")
                .build();
        Member savedMember2 = memberRepository.save(member2);

        Rental rental = Rental.builder()
                .rentalDeadLine(LocalDateTime.now())
                .rentalState(RentalState.TRADING)
                .merchantId(savedMember2.getId())
                .book(savedBook)
                .customer(savedMember)
                .build();
        Rental savedRental = rentalRepository.save(rental);

        Rental rental2 = Rental.builder()
                .rentalDeadLine(LocalDateTime.now())
                .rentalState(RentalState.TRADING)
                .merchantId(savedMember2.getId())
                .book(savedBook2)
                .customer(savedMember)
                .build();
        Rental savedRental2 = rentalRepository.save(rental2);

        Rental rental3 = Rental.builder()
                .rentalDeadLine(LocalDateTime.now())
                .rentalState(RentalState.TRADING)
                .merchantId(savedMember2.getId())
                .book(savedBook3)
                .customer(savedMember)
                .build();
        Rental savedRental3 = rentalRepository.save(rental3);

        Rental rental4 = Rental.builder()
                .rentalDeadLine(LocalDateTime.now())
                .rentalState(RentalState.TRADING)
                .merchantId(savedMember2.getId())
                .book(savedBook4)
                .customer(savedMember)
                .build();
        Rental savedRental4 = rentalRepository.save(rental4);

        Rental rental5 = Rental.builder()
                .rentalDeadLine(LocalDateTime.now())
                .rentalState(RentalState.TRADING)
                .merchantId(savedMember2.getId())
                .book(savedBook5)
                .customer(savedMember)
                .build();
        Rental savedRental5 = rentalRepository.save(rental5);

        Rental rental6 = Rental.builder()
                .rentalDeadLine(LocalDateTime.now())
                .rentalState(RentalState.TRADING)
                .merchantId(savedMember2.getId())
                .book(savedBook)
                .customer(savedMember)
                .build();
        Rental savedRental6 = rentalRepository.save(rental6);

        Rental rental7 = Rental.builder()
                .rentalDeadLine(LocalDateTime.now())
                .rentalState(RentalState.TRADING)
                .merchantId(savedMember2.getId())
                .book(savedBook7)
                .customer(savedMember)
                .build();
        Rental savedRental7 = rentalRepository.save(rental7);

        Member member3 = Member.builder()
                .userId("test3")
                .avatarUrl("aaa@aa.com")
                .nickname("tester3")
                .password("12341234")
                .build();
        Member savedMember3 = memberRepository.save(member3);

        Reservation reservation = Reservation.create(savedRental, savedMember3, savedBook);
        reservationRepository.save(reservation);
        Reservation reservation2 = Reservation.create(savedRental2, savedMember3, savedBook2);
        reservationRepository.save(reservation2);
        Reservation reservation3 = Reservation.create(savedRental3, savedMember3, savedBook3);
        reservationRepository.save(reservation3);
        Reservation reservation4 = Reservation.create(savedRental4, savedMember3, savedBook4);
        reservationRepository.save(reservation4);
        Reservation reservation5 = Reservation.create(savedRental5, savedMember3, savedBook5);
        reservationRepository.save(reservation5);
        Reservation reservation6 = Reservation.create(savedRental6, savedMember3, savedBook6);
        reservationRepository.save(reservation6);
        Reservation reservation7 = Reservation.create(savedRental7, savedMember3, savedBook7);
        reservationRepository.save(reservation7);
    }

    @Test
    public void getReservationByRentalIdTest() {
        // given
        Long rentalId = 1L;

        // when
        List<Reservation> reservationList = reservationQueryRepository.getReservationByRentalId(rentalId);

        // then
        assertThat(reservationList).hasSize(1);
    }

    @Test
    public void getReservationByIdTest() {
        // given
        Long reservationId = 1L;

        // when
        Reservation foundReservation = reservationQueryRepository.getReservationById(reservationId);

        // then
        assertEquals(foundReservation.getId(), reservationId);
    }


    @Test
    public void ltReservationIdFalseTest() {
        // given
        Long memberId = 3L;
        PageRequestImpl pageRequest = new PageRequestImpl(1L);

        // when
        SliceImpl<ReservationInfoResponse> reservationInfoResponseSlice = reservationQueryRepository.findAllByMemberIdOrderByIdDesc(memberId, pageRequest);

        // then
        assertThat(reservationInfoResponseSlice.hasNext()).isEqualTo(false);
    }

    @Test
    public void ltReservationIdTrueTest() {
        // given
        Long memberId = 3L;
        PageRequestImpl pageRequest = new PageRequestImpl(null);

        // when
        SliceImpl<ReservationInfoResponse> reservationInfoResponseSlice = reservationQueryRepository.findAllByMemberIdOrderByIdDesc(memberId, pageRequest);

        // then
        assertThat(reservationInfoResponseSlice.hasNext()).isEqualTo(true);
    }

}
