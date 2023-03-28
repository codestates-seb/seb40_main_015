package com.dongnebook.domain.rental.repository;

import com.dongnebook.domain.book.adapter.out.BookCommandRepository;
import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.book.domain.BookProduct;
import com.dongnebook.domain.book.domain.Money;
import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.member.repository.MemberRepository;
import com.dongnebook.domain.model.Location;
import com.dongnebook.domain.rental.domain.Rental;
import com.dongnebook.domain.rental.domain.RentalState;
import com.dongnebook.support.DataClearExtension;
import com.dongnebook.support.DatabaseCleaner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(showSql = false)
@Import(DatabaseCleaner.class)
@ExtendWith(DataClearExtension.class)
public class RentalRepositoryTest {
    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private BookCommandRepository bookCommandRepository;

    static Rental rental;

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

        rental = Rental.builder()
                .rentalDeadLine(LocalDateTime.now())
                .rentalState(RentalState.TRADING)
                .merchantId(savedMember2.getId())
                .book(savedBook)
                .customer(savedMember)
                .build();
    }

    @Test
    public void saveRentalTest() {
        // given

        // when
        Rental savedRental = rentalRepository.save(rental);

        // then
        assertNotNull(savedRental);
        assertEquals(savedRental.getRentalDeadLine(), rental.getRentalDeadLine());
        assertEquals(savedRental.getRentalState(), rental.getRentalState());
        assertEquals(savedRental.getMerchantId(), rental.getMerchantId());
        assertEquals(savedRental.getBook(), rental.getBook());
        assertEquals(savedRental.getCustomer(), rental.getCustomer());
    }

    @Test
    public void rentalFindByIdTest() {
        // given
        Rental savedRental = rentalRepository.save(rental);

        // when
        Optional<Rental> foundRental = rentalRepository.findById(savedRental.getId());

        // then
        assertTrue(foundRental.isPresent());
        assertEquals(foundRental.get().getRentalDeadLine(), rental.getRentalDeadLine());
        assertEquals(foundRental.get().getRentalState(), rental.getRentalState());
        assertEquals(foundRental.get().getMerchantId(), rental.getMerchantId());
        assertEquals(foundRental.get().getBook(), rental.getBook());
        assertEquals(foundRental.get().getCustomer(), rental.getCustomer());
    }

}
