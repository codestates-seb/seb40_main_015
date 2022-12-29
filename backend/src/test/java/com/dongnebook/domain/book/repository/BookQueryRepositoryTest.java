package com.dongnebook.domain.book.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.SliceImpl;

import com.dongnebook.config.TestConfig;
import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.book.dto.request.BookSearchCondition;
import com.dongnebook.domain.book.dto.response.BookDetailResponse;
import com.dongnebook.domain.book.dto.response.BookSimpleResponse;
import com.dongnebook.domain.book.exception.BookNotFoundException;
import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.member.dto.request.MemberEditRequest;
import com.dongnebook.domain.member.exception.MemberNotFoundException;
import com.dongnebook.domain.member.repository.MemberRepository;
import com.dongnebook.domain.model.Location;
import com.dongnebook.global.dto.request.PageRequest;
import com.dongnebook.support.BookStub;
import com.dongnebook.support.DataClearExtension;
import com.dongnebook.support.DatabaseCleaner;
import com.dongnebook.support.LocationStub;
import com.dongnebook.support.MemberStub;
import com.github.gavlyukovskiy.boot.jdbc.decorator.DataSourceDecoratorAutoConfiguration;

@DataJpaTest(showSql = false)
// @SpringBootTest
@ImportAutoConfiguration(DataSourceDecoratorAutoConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({TestConfig.class, DatabaseCleaner.class})
@ExtendWith(DataClearExtension.class)
	// @Rollback
class BookQueryRepositoryTest {

	@Autowired
	BookQueryRepository bookQueryRepository;
	@Autowired
	BookCommandRepository bookCommandRepository;
	@Autowired
	MemberRepository memberRepository;
	@PersistenceContext
	EntityManager entityManager;

	private Member member;
	private Book book;

	@BeforeEach
	void setUp() {
		Arrays.stream(MemberStub.values()).forEach(value -> {
			member = value.of();
			memberRepository.save(member);
		});

		Arrays.stream(BookStub.values()).forEach(value -> {
			book = value.of();
			bookCommandRepository.save(book);
		});
	}

	// @AfterEach
	// void tearDown() {
	// 	// bookCommandRepository.deleteAll();
	// 	// memberRepository.deleteAll();
	// 	// Cache cache = entityManager.getEntityManager().getEntityManagerFactory().getCache();
	// 	// System.out.println("cache = " + cache.contains(Member.class,1L));
	// 	entityManager.clear();
	// }

	// @Test
	// void getWithMerchantByBookId() {
	//
	// }

	@Test
	@DisplayName("책을 상세조회한다.")
	void getBookDetail() {

		//given,when
		BookDetailResponse bookDetail = bookQueryRepository.getBookDetail(1L, 1L).orElseThrow(BookNotFoundException::new);
		//then
		assertAll(
			() -> assertThat(bookDetail.getBook().getTitle()).isEqualTo("자바의 정석"),
			() -> assertThat(bookDetail.getBook().getAuthor()).isEqualTo("남궁성")
		);
	}

	@Test
	@DisplayName("주변에 있는 책을 조회한다.")
	void getNearByBookLocation() {
		//given
		Location 봉천역 = LocationStub.봉천역.of();
		BookSearchCondition condition = BookSearchCondition.builder().bookTitle(null).height(40).width(40)
			.latitude(봉천역.getLatitude()).longitude(봉천역.getLongitude()).level(3).build();
		//when
		List<Location> nearByBookLocation = bookQueryRepository.getNearByBookLocation(condition);
		//then
		assertThat(nearByBookLocation).hasSize(7);
	}

	@Test
	@DisplayName("모든 책을 조회한다.")
	void getAll() {
		//given
		BookSearchCondition condition = BookSearchCondition.builder().bookTitle(null).height(null).width(null)
			.latitude(null).longitude(null).level(null).build();

		//when
		SliceImpl<BookSimpleResponse> result = bookQueryRepository.getAll(condition, new PageRequest(null));

		//then
		assertAll(
			() -> assertThat(result).hasSize(6),
			() -> assertThat(result.isLast()).isFalse(),
			() -> assertThat(result.hasNext()).isTrue(),
			() -> assertThat(result.getContent().get(5).getTitle()).isEqualTo("씨의 정석")
		);

	}

	@Test
	@DisplayName("삭제된 책은 검색되지 않는다.")
	void getRentableBook() {
		//given
		Book book1 = bookCommandRepository.findById(1L).get();
		book1.delete();
		BookSearchCondition condition = BookSearchCondition.builder().bookTitle(null).height(null).width(null)
			.latitude(null).longitude(null).level(null).build();

		//when
		SliceImpl<BookSimpleResponse> result = bookQueryRepository.getAll(condition, new PageRequest(null));

		//then
		assertThat(result).hasSize(6);
		assertThat(result.isLast()).isTrue();
	}

	@Test
	@DisplayName("책 한권을 검색한다.")
	void getAll2() {
		//given
		BookSearchCondition condition = BookSearchCondition.builder().bookTitle("씨의 정석").height(null).width(null)
			.latitude(null).longitude(null).level(null).build();

		//when
		SliceImpl<BookSimpleResponse> result = bookQueryRepository.getAll(condition, new PageRequest(null));

		//then
		assertThat(result.isLast()).isTrue();
		assertThat(result.hasNext()).isFalse();
		assertThat(result.getContent().get(0).getTitle()).isEqualTo("씨의 정석");
	}

	@Test
	@DisplayName("회원이 가지고 있는 책을 조회한다.")
	void getListByMember() {
	    // given
		Member member1 = memberRepository.findById(1L).orElseThrow(MemberNotFoundException::new);

		// when
		SliceImpl<BookSimpleResponse> listByMember = bookQueryRepository.getListByMember(member1.getId(),
			new PageRequest(null));

		// then
		assertThat(listByMember.getContent().get(0).getTitle()).isEqualTo("자바의 정석");
		assertThat(listByMember.hasNext()).isFalse();

	}
	@Test
	@DisplayName("서점의 주소를 변경한다.")
	void updateBookLocation() {
		//given
		Member member1 = memberRepository.findById(1L).orElseThrow(MemberNotFoundException::new);
		Location samsung = LocationStub.삼성전자봉천역점.of();
		member1.edit(MemberEditRequest.builder()
			.address("서울시 관악구 삼성전")
			.location(samsung)
			.build());

		//when
		bookQueryRepository.updateBookLocation(member1,member1.getLocation());
		entityManager.clear();
		Book book = bookCommandRepository.findById(1L).orElseThrow(BookNotFoundException::new);

		//then
		assertThat(book.getLocation().getLatitude()).isEqualTo(samsung.getLatitude());
		assertThat(book.getLocation().getLongitude()).isEqualTo(samsung.getLongitude());
	}
}