package com.dongnebook.domain.reservation.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

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
import com.dongnebook.support.DataClearExtension;
import com.dongnebook.support.DatabaseCleaner;

@DataJpaTest(showSql = false)
@Import(DatabaseCleaner.class)
@ExtendWith(DataClearExtension.class)
class ReservationRepositoryTest {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private BookCommandRepository bookCommandRepository;
    @Autowired
    private RentalRepository rentalRepository;

    static Reservation reservation;

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

        Member member3 = Member.builder()
                .userId("test3")
                .avatarUrl("aaa@aa.com")
                .nickname("tester3")
                .password("12341234")
                .build();
        Member savedMember3 = memberRepository.save(member3);

        reservation = Reservation.create(savedRental, savedMember3, savedBook);
    }

    @Test
    void saveReservationTest() {
        // given
        // when
        Reservation savedReservation = reservationRepository.save(reservation);

        // then
        assertNotNull(savedReservation);
        assertEquals(savedReservation.getRentalExpectedAt(), reservation.getRentalExpectedAt());
        assertEquals(savedReservation.getReturnExpectedAt(), reservation.getReturnExpectedAt());
        assertEquals(savedReservation.getRental(), reservation.getRental());
        assertEquals(savedReservation.getMember(), reservation.getMember());
        assertEquals(savedReservation.getBook(), reservation.getBook());
    }

    @Test
    void reservationFindByIdTest() {
        // given
        Reservation savedReservation = reservationRepository.save(reservation);

        // when
        Optional<Reservation> optionalReservation = reservationRepository.findById(savedReservation.getId());

        // then
        assertTrue(optionalReservation.isPresent());
        assertEquals(optionalReservation.get().getRentalExpectedAt(), savedReservation.getRentalExpectedAt());
        assertEquals(optionalReservation.get().getReturnExpectedAt(), savedReservation.getReturnExpectedAt());
        assertEquals(optionalReservation.get().getRental(), savedReservation.getRental());
        assertEquals(optionalReservation.get().getMember(), savedReservation.getMember());
        assertEquals(optionalReservation.get().getBook(), savedReservation.getBook());
    }

    @Test
    void deleteReservationTest() {
        // given
        Reservation savedReservation = reservationRepository.save(reservation);

        // when
        reservationRepository.delete(savedReservation);

        // then
        Optional<Reservation> optionalReservation = reservationRepository.findById(savedReservation.getId());
        assertTrue(optionalReservation.isEmpty());
    }

}
