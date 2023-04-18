package com.dongnebook.domain.book.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.SliceImpl;

import com.dongnebook.config.TestConfig;
import com.dongnebook.domain.book.adapter.out.BookCommandRepository;
import com.dongnebook.domain.book.adapter.out.BookQueryRepository;
import com.dongnebook.domain.book.application.port.in.response.BookDetailResponse;
import com.dongnebook.domain.book.application.port.in.response.BookSimpleResponse;
import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.book.domain.BookProduct;
import com.dongnebook.domain.book.domain.Money;
import com.dongnebook.domain.dibs.domain.Dibs;
import com.dongnebook.domain.dibs.repository.DibsRepository;
import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.member.repository.MemberRepository;
import com.dongnebook.domain.model.Location;
import com.dongnebook.global.dto.request.PageRequestImpl;
import com.dongnebook.support.DataClearExtension;
import com.dongnebook.support.DatabaseCleaner;
import com.github.gavlyukovskiy.boot.jdbc.decorator.DataSourceDecoratorAutoConfiguration;


@DataJpaTest(showSql = false)
@ImportAutoConfiguration(DataSourceDecoratorAutoConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({TestConfig.class, DatabaseCleaner.class})
@ExtendWith(DataClearExtension.class)
class BookQueryRepositoryTest {
    @Autowired
    BookQueryRepository bookQueryRepository;
    @Autowired
    BookCommandRepository bookCommandRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    DibsRepository dibsRepository;

    static Member savedMerchant;
    static Member savedNewCustomer;
    static Book savedBook;
    static Book savedBook8;
    static Dibs savedDibs;

    @BeforeEach
    public void setting() {
        Member merchant = Member.builder()
                .userId("test1")
                .avatarUrl("aaa1@aa.com")
                .nickname("tester1")
                .password("12341234")
                .build();
        savedMerchant = memberRepository.save(merchant);

        BookProduct bookProduct = new BookProduct("수학의 정석1", "남궁성", "도우출판");
        Book book = Book.builder()
                .id(1L)
                .bookProduct(bookProduct)
                .imgUrl("aaa@abc.com")
                .description("기본이 중요")
                .rentalFee(Money.of(1000))
                .location(new Location(37.5340, 126.7064))
                .member(savedMerchant)
                .build();
        savedBook = bookCommandRepository.save(book);

        Member customer = Member.builder()
                .userId("test2")
                .avatarUrl("aaa2@aa.com")
                .nickname("tester2")
                .password("12341234")
                .build();
        Member savedCustomer = memberRepository.save(customer);

        Member newCustomer = Member.builder()
                .userId("test3")
                .avatarUrl("aaa3@aa.com")
                .nickname("tester3")
                .password("12341234")
                .build();
        savedNewCustomer = memberRepository.save(newCustomer);

        Dibs dibs = Dibs.of(savedNewCustomer, savedBook);
        dibsRepository.save(dibs);

        BookProduct bookProduct2 = new BookProduct("수학의 정석2", "남궁성", "도우출판");
        Book book2 = Book.builder()
                .id(2L)
                .bookProduct(bookProduct2)
                .imgUrl("aaa@abc.com")
                .description("기본이 중요")
                .rentalFee(Money.of(1000))
                .location(new Location(37.5340, 126.7064))
                .member(savedMerchant)
                .build();
        Book savedBook2 = bookCommandRepository.save(book2);

        Dibs dibs2 = Dibs.of(savedNewCustomer, savedBook2);
        dibsRepository.save(dibs2);

        BookProduct bookProduct3 = new BookProduct("수학의 정석3", "남궁성", "도우출판");
        Book book3 = Book.builder()
                .id(3L)
                .bookProduct(bookProduct3)
                .imgUrl("aaa@abc.com")
                .description("기본이 중요")
                .rentalFee(Money.of(1000))
                .location(new Location(37.5340, 126.7064))
                .member(savedMerchant)
                .build();
        Book savedBook3 = bookCommandRepository.save(book3);

        Dibs dibs3 = Dibs.of(savedNewCustomer, savedBook3);
        dibsRepository.save(dibs3);

        BookProduct bookProduct4 = new BookProduct("수학의 정석4", "남궁성", "도우출판");
        Book book4 = Book.builder()
                .id(4L)
                .bookProduct(bookProduct4)
                .imgUrl("aaa@abc.com")
                .description("기본이 중요")
                .rentalFee(Money.of(1000))
                .location(new Location(37.5340, 126.7064))
                .member(savedMerchant)
                .build();
        Book savedBook4 = bookCommandRepository.save(book4);

        Dibs dibs4 = Dibs.of(savedNewCustomer, savedBook4);
        dibsRepository.save(dibs4);

        BookProduct bookProduct5 = new BookProduct("수학의 정석5", "남궁성", "도우출판");
        Book book5 = Book.builder()
                .id(5L)
                .bookProduct(bookProduct5)
                .imgUrl("aaa@abc.com")
                .description("기본이 중요")
                .rentalFee(Money.of(1000))
                .location(new Location(37.5340, 126.7064))
                .member(savedMerchant)
                .build();
        Book savedBook5 = bookCommandRepository.save(book5);

        Dibs dibs5 = Dibs.of(savedNewCustomer, savedBook5);
        dibsRepository.save(dibs5);

        BookProduct bookProduct6 = new BookProduct("수학의 정석6", "남궁성", "도우출판");
        Book book6 = Book.builder()
                .id(6L)
                .bookProduct(bookProduct6)
                .imgUrl("aaa@abc.com")
                .description("기본이 중요")
                .rentalFee(Money.of(1000))
                .location(new Location(37.5340, 126.7064))
                .member(savedMerchant)
                .build();
        Book savedBook6 = bookCommandRepository.save(book6);

        Dibs dibs6 = Dibs.of(savedNewCustomer, savedBook6);
        dibsRepository.save(dibs6);

        BookProduct bookProduct7 = new BookProduct("수학의 정석7", "남궁성", "도우출판");
        Book book7 = Book.builder()
                .id(7L)
                .bookProduct(bookProduct7)
                .imgUrl("aaa@abc.com")
                .description("기본이 중요")
                .rentalFee(Money.of(1000))
                .location(new Location(37.5340, 126.7064))
                .member(savedMerchant)
                .build();
        Book savedBook7 = bookCommandRepository.save(book7);

        Dibs dibs7 = Dibs.of(savedNewCustomer, savedBook7);
        dibsRepository.save(dibs7);

        BookProduct bookProduct8 = new BookProduct("수학의 정석8", "남궁성", "도우출판");
        Book book8 = Book.builder()
                .id(8L)
                .bookProduct(bookProduct8)
                .imgUrl("aaa@abc.com")
                .description("기본이 중요")
                .rentalFee(Money.of(1000))
                .location(new Location(37.5340, 126.7064))
                .member(savedMerchant)
                .build();
        savedBook8 = bookCommandRepository.save(book8);

        Dibs dibs8 = Dibs.of(savedNewCustomer, savedBook8);
        savedDibs = dibsRepository.save(dibs8);

    }

    @Test
    void findWithMerchantByBookIdTest() {
        // given

        // when
        Optional<Book> bookOptional = bookQueryRepository.findWithMerchantByBookId(savedBook.getId());

        // then
        assertTrue(bookOptional.isPresent());
    }

    @Test
    void findBookDetailTest() {
        // given

        // when
        Optional<BookDetailResponse> bookDetailResponseOptional = bookQueryRepository.findBookDetail(1L, null);

        // then
        assertTrue(bookDetailResponseOptional.isPresent());
    }

    @Test
    void getAllDibsBookTest1() {
        // given
        PageRequestImpl pageRequest = new PageRequestImpl(0L);

        // when
        SliceImpl<BookSimpleResponse> bookSimpleResponses = bookQueryRepository.getAllDibsBook(1L, pageRequest);

        // then
        assertFalse(bookSimpleResponses.hasNext());
    }

    @Test
    void getAllDibsBookTest2() {
        // given
        PageRequestImpl pageRequest = new PageRequestImpl(null);

        // when
        SliceImpl<BookSimpleResponse> bookSimpleResponses = bookQueryRepository.getAllDibsBook(savedNewCustomer.getId(), pageRequest);

        // then
        assertTrue(bookSimpleResponses.hasNext());
    }

    @Test
    void getListByMemberTest1() {
        // given
        PageRequestImpl pageRequest = new PageRequestImpl(savedBook8.getId());

        // when
        SliceImpl<BookSimpleResponse> bookSimpleResponses = bookQueryRepository.getListByMember(savedMerchant.getId(), pageRequest);

        // then
        assertTrue(bookSimpleResponses.hasNext());
    }

    @Test
    void getListByMemberTest2() {
        // given
        PageRequestImpl pageRequest = new PageRequestImpl(null);

        // when
        SliceImpl<BookSimpleResponse> bookSimpleResponses = bookQueryRepository.getListByMember(savedMerchant.getId(), pageRequest);

        // then
        assertTrue(bookSimpleResponses.hasNext());
    }
}
