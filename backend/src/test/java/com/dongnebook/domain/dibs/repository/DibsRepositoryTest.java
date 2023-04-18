package com.dongnebook.domain.dibs.repository;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

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
import com.dongnebook.domain.dibs.domain.Dibs;
import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.member.repository.MemberRepository;
import com.dongnebook.domain.model.Location;
import com.dongnebook.support.DataClearExtension;
import com.dongnebook.support.DatabaseCleaner;

@DataJpaTest(showSql = false)
@Import(DatabaseCleaner.class)
@ExtendWith(DataClearExtension.class)
class DibsRepositoryTest {
    @Autowired
    private DibsRepository dibsRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private BookCommandRepository bookCommandRepository;

    static Book savedBook;
    static Member savedNewCustomer;
    static Dibs dibs;

    @BeforeEach
    public void setting() {
        Member merchant = Member.builder()
                .userId("test1")
                .avatarUrl("aaa1@aa.com")
                .nickname("tester1")
                .password("12341234")
                .build();
        Member savedMerchant = memberRepository.save(merchant);

        BookProduct bookProduct = new BookProduct("수학의 정석1", "남궁성", "도우출판");
        Book book = Book.builder()
                .id(1L)
                .bookProduct(bookProduct)
                .imgUrl("aaa@abc.com")
                .description("기본이 중요하다")
                .rentalFee(Money.of(1000))
                .location(new Location(37.5340, 126.7064))
                .member(savedMerchant)
                .build();
        savedBook = bookCommandRepository.save(book);

        Member newCustomer = Member.builder()
                .userId("test3")
                .avatarUrl("aaa3@aa.com")
                .nickname("tester3")
                .password("12341234")
                .build();
        savedNewCustomer = memberRepository.save(newCustomer);

        dibs = Dibs.of(savedNewCustomer, savedBook);
    }

    @Test
    void saveDibsTest() {
        // given

        // when
        Dibs savedDibs = dibsRepository.save(dibs);

        // then
        assertNotNull(savedDibs);
        assertEquals(savedDibs.getId(), 1L);
        assertEquals(savedDibs.getBook(), savedBook);
        assertEquals(savedDibs.getMember(), savedNewCustomer);
    }

    @Test
    void findByBookAndMemberOrderByIdDesc() {
        // given
        Dibs savedDibs = dibsRepository.save(dibs);

        // when
        Optional<Dibs> foundDibsOptional = dibsRepository.findByBookAndMemberOrderByIdDesc(savedBook, savedNewCustomer);

        // then
        assertTrue(foundDibsOptional.isPresent());
        assertEquals(foundDibsOptional.get().getId(), savedDibs.getId());
        assertEquals(foundDibsOptional.get().getBook(), savedDibs.getBook());
        assertEquals(foundDibsOptional.get().getMember(), savedDibs.getMember());
        assertEquals(foundDibsOptional.get().getCreatedAt(), savedDibs.getCreatedAt());
        assertEquals(foundDibsOptional.get().getModifiedAt(), savedDibs.getModifiedAt());
    }

    @Test
    void deleteDibsTest() {
        // given
        Dibs savedDibs = dibsRepository.save(dibs);

        // when
        dibsRepository.delete(savedDibs);
        Optional<Dibs> foundDibsOptional = dibsRepository.findByBookAndMemberOrderByIdDesc(savedBook, savedNewCustomer);

        // then
        assertTrue(foundDibsOptional.isEmpty());
    }
}
