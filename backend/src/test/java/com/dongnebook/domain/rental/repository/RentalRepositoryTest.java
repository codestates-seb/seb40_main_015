package com.dongnebook.domain.rental.repository;

import com.dongnebook.domain.book.adapter.out.BookCommandRepository;
import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.book.domain.BookProduct;
import com.dongnebook.domain.book.domain.Money;
import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.member.dto.request.MemberRegisterRequest;
import com.dongnebook.domain.member.repository.MemberRepository;
import com.dongnebook.domain.model.Location;
import com.dongnebook.domain.rental.domain.Rental;
import com.dongnebook.domain.rental.domain.RentalState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
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

//      안 되는 애
//    @Test
//    public void saveRentalTestOriginal() {
//        // given
//        Member merchant = new Member(1L, "test1", "tester1", new Location(37.5340, 126.7064), "anywhere", "abc1@abc.com");
//        BookProduct bookProduct = new BookProduct("수학의 정석", "남궁성", "도우출판");
//        Money money = Money.of(1000);
//        Location location = new Location(37.5340, 126.7064);
//        Book book = new Book(1L, bookProduct, "aaa@abc.com", "기본이 중요하다", money, location, merchant);
//        Member customer = new Member(2L, "test2", "tester2", new Location(37.5340, 126.7064), "anywhere", "abc2@abc.com");
//
//        // when
//        Rental savedRental = rentalRepository.save(Rental.create(book, customer));
//
//        // then
//        assertNotNull(savedRental);
//    }

//    되는 애 (다만, 뒤에 것이 다음 테스트와 일관성이 있어서 그렇게 만듦)
//    @Test
//    public void saveRentalTestOriginal2() {
//        // given
//        MemberRegisterRequest memberRegisterRequest1 = new MemberRegisterRequest("merchant", "12341234", "nickname1");
//        Member merchant = Member.create(memberRegisterRequest1);
//        Member savedMerchant = memberRepository.save(merchant);
//        BookProduct bookProduct = new BookProduct("수학의 정석", "남궁성", "도우출판");
//        Book book = new Book(1L, bookProduct, "aaa@abc.com", "기본이 중요하다", Money.of(1000), new Location(37.5340, 126.7064), savedMerchant);
//        Book savedBook = bookCommandRepository.save(book);
//
//        MemberRegisterRequest memberRegisterRequest2 = new MemberRegisterRequest("customer", "12341234", "nickname2");
//        Member customer = Member.create(memberRegisterRequest2);
//        Member savedCustomer = memberRepository.save(customer);
//
//        Rental rental = Rental.create(savedBook, savedCustomer);
//
//        // when
//        Rental savedRental = rentalRepository.save(rental);
//
//        // then
//        assertNotNull(savedRental);
//        assertEquals(savedRental.getRentalDeadLine(), rental.getRentalDeadLine());
//        assertEquals(savedRental.getRentalState(), rental.getRentalState());
//        assertEquals(savedRental.getMerchantId(), rental.getMerchantId());
//        assertEquals(savedRental.getBook(), rental.getBook());
//        assertEquals(savedRental.getCustomer(), rental.getCustomer());
//    }

    @Test
    public void saveRentalTest() {
        // given
//        Member member1 = Member.builder()
//                .userId("test")
//                .avatarUrl("aaa@aa.com")
//                .nickname("tester")
//                .password("12341234")
//                .build();
//        Member savedMember = memberRepository.save(member1);
//
//        BookProduct bookProduct = new BookProduct("수학의 정석1", "남궁성", "도우출판");
//        Book book = Book.builder()
//                .id(1L)
//                .bookProduct(bookProduct)
//                .imgUrl("aaa@abc.com")
//                .description("기본이 중요하다")
//                .rentalFee(Money.of(1000))
//                .location(new Location(37.5340, 126.7064))
//                .member(savedMember)
//                .build();
//        Book savedBook = bookCommandRepository.save(book);
//
//        Member member2 = Member.builder()
//                .userId("test2")
//                .avatarUrl("aaa@aa.com")
//                .nickname("tester2")
//                .password("12341234")
//                .build();
//        Member savedMember2 = memberRepository.save(member2);

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

//  개별로 하면 되지만, class 단위로 하면 안 되는 애 (이름 상관없이)
//    @Test
//    public void rentalFindByIdTestOriginal() {
//        // given
//        MemberRegisterRequest memberRegisterRequest1 = new MemberRegisterRequest("merchant", "12341234", "nickname1");
//        Member merchant = Member.create(memberRegisterRequest1);
//        Member savedMerchant = memberRepository.save(merchant);
//        BookProduct bookProduct = new BookProduct("수학의 정석1", "남궁성", "도우출판");
//        Book book = new Book(1L, bookProduct, "aaa@abc.com", "기본이 중요하다", Money.of(1000), new Location(37.5340, 126.7064), savedMerchant);
//        Book savedBook = bookCommandRepository.save(book);
//
//        MemberRegisterRequest memberRegisterRequest2 = new MemberRegisterRequest("customer", "12341234", "nickname2");
//        Member customer = Member.create(memberRegisterRequest2);
//        Member savedCustomer = memberRepository.save(customer);
//
//        Rental rental = Rental.create(savedBook, savedCustomer);
//
//        // when
//        rentalRepository.save(rental);
//        Optional<Rental> foundRental = rentalRepository.findById(1L);
//
//        // then
//        assertTrue(foundRental.isPresent());
//        assertEquals(foundRental.get().getRentalDeadLine(), rental.getRentalDeadLine());
//        assertEquals(foundRental.get().getRentalState(), rental.getRentalState());
//        assertEquals(foundRental.get().getMerchantId(), rental.getMerchantId());
//        assertEquals(foundRental.get().getBook(), rental.getBook());
//        assertEquals(foundRental.get().getCustomer(), rental.getCustomer());
//    }

    // 이름이 findByIdTset면 안되는 애
    @Test
//    public void findByIdTest() {
    public void rentalFindByIdTest() {
        // given
//        Member member1 = Member.builder()
//                .userId("test")
//                .avatarUrl("aaa@aa.com")
//                .nickname("tester")
//                .password("12341234")
//                .build();
//        Member savedMember = memberRepository.save(member1);
//
//        BookProduct bookProduct = new BookProduct("수학의 정석1", "남궁성", "도우출판");
//        Book book = Book.builder()
//                .id(1L)
//                .bookProduct(bookProduct)
//                .imgUrl("aaa@abc.com")
//                .description("기본이 중요하다")
//                .rentalFee(Money.of(1000))
//                .location(new Location(37.5340, 126.7064))
//                .member(savedMember)
//                .build();
//        Book savedBook = bookCommandRepository.save(book);
//
//        Member member2 = Member.builder()
//                .userId("test2")
//                .avatarUrl("aaa@aa.com")
//                .nickname("tester2")
//                .password("12341234")
//                .build();
//        Member savedMember2 = memberRepository.save(member2);

        // given

        // when
        rentalRepository.save(rental);
        Optional<Rental> foundRental = rentalRepository.findById(1L);

        // then
        assertTrue(foundRental.isPresent());
        assertEquals(foundRental.get().getRentalDeadLine(), rental.getRentalDeadLine());
        assertEquals(foundRental.get().getRentalState(), rental.getRentalState());
        assertEquals(foundRental.get().getMerchantId(), rental.getMerchantId());
        assertEquals(foundRental.get().getBook(), rental.getBook());
        assertEquals(foundRental.get().getCustomer(), rental.getCustomer());
    }

}
