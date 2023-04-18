package com.dongnebook.domain.rental.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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
import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.book.domain.BookProduct;
import com.dongnebook.domain.book.domain.Money;
import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.member.repository.MemberRepository;
import com.dongnebook.domain.model.Location;
import com.dongnebook.domain.rental.domain.Rental;
import com.dongnebook.domain.rental.domain.RentalState;
import com.dongnebook.domain.rental.dto.response.RentalBookResponse;
import com.dongnebook.global.dto.request.PageRequestImpl;
import com.dongnebook.support.DataClearExtension;
import com.dongnebook.support.DatabaseCleaner;
import com.github.gavlyukovskiy.boot.jdbc.decorator.DataSourceDecoratorAutoConfiguration;

@DataJpaTest(showSql = false)
@ImportAutoConfiguration(DataSourceDecoratorAutoConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({TestConfig.class, DatabaseCleaner.class})
@ExtendWith(DataClearExtension.class)
class RentalQueryRepositoryTest {
	@Autowired
	RentalQueryRepository rentalQueryRepository;
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	BookCommandRepository bookCommandRepository;
	@Autowired
	RentalRepository rentalRepository;

	@BeforeEach
	public void setting() {
		Member merchant = Member.builder()
			.userId("test")
			.avatarUrl("aaa@aa.com")
			.nickname("tester")
			.password("12341234")
			.build();
		Member savedMerchant = memberRepository.save(merchant);

		BookProduct bookProduct1 = new BookProduct("수학의 정석1", "남궁성", "도우출판");
		Book book1 = Book.builder()
			.id(1L)
			.bookProduct(bookProduct1)
			.imgUrl("aaa@abc.com")
			.description("기본이 중요하다1")
			.rentalFee(Money.of(1000))
			.location(new Location(37.5340, 126.7064))
			.member(savedMerchant)
			.build();
		Book savedBook1 = bookCommandRepository.save(book1);

		BookProduct bookProduct2 = new BookProduct("수학의 정석2", "남궁성", "도우출판");
		Book book2 = Book.builder()
			.id(2L)
			.bookProduct(bookProduct2)
			.imgUrl("aaa@abc.com")
			.description("기본이 중요하다2")
			.rentalFee(Money.of(1000))
			.location(new Location(37.5340, 126.7064))
			.member(savedMerchant)
			.build();
		Book savedBook2 = bookCommandRepository.save(book2);

		BookProduct bookProduct3 = new BookProduct("수학의 정석3", "남궁성", "도우출판");
		Book book3 = Book.builder()
			.id(3L)
			.bookProduct(bookProduct3)
			.imgUrl("aaa@abc.com")
			.description("기본이 중요하다3")
			.rentalFee(Money.of(1000))
			.location(new Location(37.5340, 126.7064))
			.member(savedMerchant)
			.build();
		Book savedBook3 = bookCommandRepository.save(book3);

		BookProduct bookProduct4 = new BookProduct("수학의 정석4", "남궁성", "도우출판");
		Book book4 = Book.builder()
			.id(4L)
			.bookProduct(bookProduct4)
			.imgUrl("aaa@abc.com")
			.description("기본이 중요하다4")
			.rentalFee(Money.of(1000))
			.location(new Location(37.5340, 126.7064))
			.member(savedMerchant)
			.build();
		Book savedBook4 = bookCommandRepository.save(book4);

		BookProduct bookProduct5 = new BookProduct("수학의 정석5", "남궁성", "도우출판");
		Book book5 = Book.builder()
			.id(5L)
			.bookProduct(bookProduct5)
			.imgUrl("aaa@abc.com")
			.description("기본이 중요하다5")
			.rentalFee(Money.of(1000))
			.location(new Location(37.5340, 126.7064))
			.member(savedMerchant)
			.build();
		Book savedBook5 = bookCommandRepository.save(book5);

		BookProduct bookProduct6 = new BookProduct("수학의 정석6", "남궁성", "도우출판");
		Book book6 = Book.builder()
			.id(6L)
			.bookProduct(bookProduct6)
			.imgUrl("aaa@abc.com")
			.description("기본이 중요하다6")
			.rentalFee(Money.of(1000))
			.location(new Location(37.5340, 126.7064))
			.member(savedMerchant)
			.build();
		Book savedBook6 = bookCommandRepository.save(book6);

		BookProduct bookProduct7 = new BookProduct("수학의 정석7", "남궁성", "도우출판");
		Book book7 = Book.builder()
			.id(7L)
			.bookProduct(bookProduct7)
			.imgUrl("aaa@abc.com")
			.description("기본이 중요하다7")
			.rentalFee(Money.of(1000))
			.location(new Location(37.5340, 126.7064))
			.member(savedMerchant)
			.build();
		Book savedBook7 = bookCommandRepository.save(book7);

		BookProduct bookProduct8 = new BookProduct("수학의 정석8", "남궁성", "도우출판");
		Book book8 = Book.builder()
			.id(8L)
			.bookProduct(bookProduct8)
			.imgUrl("aaa@abc.com")
			.description("기본이 중요하다8")
			.rentalFee(Money.of(1000))
			.location(new Location(37.5340, 126.7064))
			.member(savedMerchant)
			.build();
		Book savedBook8 = bookCommandRepository.save(book8);

		Member customer = Member.builder()
			.userId("test2")
			.avatarUrl("aaa@aa.com")
			.nickname("tester2")
			.password("12341234")
			.build();
		Member savedCustomer = memberRepository.save(customer);

		Rental rental1 = Rental.builder()
			.rentalDeadLine(LocalDateTime.now().plusDays(9).withHour(23).withMinute(59).withSecond(59))
			.rentalState(RentalState.TRADING)
			.merchantId(savedMerchant.getId())
			.book(savedBook1)
			.customer(savedCustomer)
			.build();
		rentalRepository.save(rental1);

		Rental rental2 = Rental.builder()
			.rentalDeadLine(LocalDateTime.now().plusDays(9).withHour(23).withMinute(59).withSecond(59))
			.rentalState(RentalState.TRADING)
			.merchantId(savedMerchant.getId())
			.book(savedBook2)
			.customer(savedCustomer)
			.build();
		rentalRepository.save(rental2);

		Rental rental3 = Rental.builder()
			.rentalDeadLine(LocalDateTime.now().withHour(23).withMinute(59).withSecond(59))
			.rentalState(RentalState.BEING_RENTED)
			.merchantId(savedMerchant.getId())
			.book(savedBook3)
			.customer(savedCustomer)
			.build();
		rentalRepository.save(rental3);

		Rental rental4 = Rental.builder()
			.rentalDeadLine(LocalDateTime.now().plusDays(9).withHour(23).withMinute(59).withSecond(59))
			.rentalState(RentalState.TRADING)
			.merchantId(savedMerchant.getId())
			.book(savedBook4)
			.customer(savedCustomer)
			.build();
		rentalRepository.save(rental4);

		Rental rental5 = Rental.builder()
			.rentalDeadLine(LocalDateTime.now().plusDays(9).withHour(23).withMinute(59).withSecond(59))
			.rentalState(RentalState.TRADING)
			.merchantId(savedMerchant.getId())
			.book(savedBook5)
			.customer(savedCustomer)
			.build();
		rentalRepository.save(rental5);

		Rental rental6 = Rental.builder()
			.rentalDeadLine(LocalDateTime.now().plusDays(9).withHour(23).withMinute(59).withSecond(59))
			.rentalState(RentalState.TRADING)
			.merchantId(savedMerchant.getId())
			.book(savedBook6)
			.customer(savedCustomer)
			.build();
		rentalRepository.save(rental6);

		Rental rental7 = Rental.builder()
			.rentalDeadLine(LocalDateTime.now().plusDays(9).withHour(23).withMinute(59).withSecond(59))
			.rentalState(RentalState.TRADING)
			.merchantId(savedMerchant.getId())
			.book(savedBook7)
			.customer(savedCustomer)
			.build();
		rentalRepository.save(rental7);

		Rental rental8 = Rental.builder()
			.rentalDeadLine(LocalDateTime.now().plusDays(9).withHour(23).withMinute(59).withSecond(59))
			.rentalState(RentalState.TRADING)
			.merchantId(savedMerchant.getId())
			.book(savedBook8)
			.customer(savedCustomer)
			.build();
		rentalRepository.save(rental8);
	}

	@Test
	void findRentalByIdTest() {
		// given
		Long rentalId = 1L;

		// when
		Optional<Rental> rentalOptional = rentalQueryRepository.findRentalById(rentalId);

		// then
		assertTrue(rentalOptional.isPresent());
	}

	@Test
	void getRentalByBookIdTest() {
		// given
		Long bookId = 1L;

		// when
		List<Rental> rentalList = rentalQueryRepository.getRentalByBookId(bookId);

		// then
		assertThat(rentalList).hasSize(1);
	}

	@Test
	void findAllDeadLineRentalTest() {
		// given
		LocalDate localDate = LocalDate.now();

		// when
		List<Rental> rentalList = rentalQueryRepository.findAllDeadLineRental(localDate);

		// then
		assertThat(rentalList).hasSize(1);
	}

	@Test
	void findAllByMerchantIdOrderByIdDescTest1() {
		// given
		Long merchantId = 1L;
		String rentalState = (RentalState.TRADING).toString();
		PageRequestImpl pageRequest = PageRequestImpl.of(null);

		// when
		SliceImpl<RentalBookResponse> rentalBookResponses = rentalQueryRepository.findAllByMerchantIdOrderByIdDesc(
			merchantId, rentalState, pageRequest);

		// then
		assertThat(rentalBookResponses.hasNext()).isTrue();
	}

	@Test
	void findAllByMerchantIdOrderByIdDescTest2() {
		// given
		Long merchantId = 1L;
		String rentalState = (RentalState.TRADING).toString();
		PageRequestImpl pageRequest = PageRequestImpl.of(2L);

		// when
		SliceImpl<RentalBookResponse> rentalBookResponses = rentalQueryRepository.findAllByMerchantIdOrderByIdDesc(
			merchantId, rentalState, pageRequest);

		// then
		assertThat(rentalBookResponses.hasNext()).isFalse();
	}

	@Test
	void findAllByCustomerIdOrderByIdDesc() {
		// given
		Long customerId = 2L;
		String rentalState = (RentalState.NONE).toString();
		PageRequestImpl pageRequest = PageRequestImpl.of(4L);

		// when
		SliceImpl<RentalBookResponse> rentalBookResponses = rentalQueryRepository.findAllByCustomerIdOrderByIdDesc(
			customerId, rentalState, pageRequest);

		// then
		assertThat(rentalBookResponses.hasNext()).isFalse();
	}

	@Test
	void findAllByCustomerIdOrderByIdDesc2() {
		// given
		Long customerId = 2L;
		String rentalState = (RentalState.TRADING).toString();
		PageRequestImpl pageRequest = PageRequestImpl.of(null);

		// when
		SliceImpl<RentalBookResponse> rentalBookResponses = rentalQueryRepository.findAllByCustomerIdOrderByIdDesc(
			customerId, rentalState, pageRequest);

		// then
		assertThat(rentalBookResponses.hasNext()).isTrue();
	}

}
