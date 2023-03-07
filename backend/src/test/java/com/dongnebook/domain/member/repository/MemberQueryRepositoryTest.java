package com.dongnebook.domain.member.repository;

import static org.assertj.core.api.Assertions.*;

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
import com.dongnebook.domain.book.repository.BookCommandRepository;
import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.member.dto.request.MerchantSearchRequest;
import com.dongnebook.domain.member.dto.response.MemberResponse;
import com.dongnebook.domain.model.Location;
import com.dongnebook.domain.rental.repository.RentalRepository;
import com.dongnebook.global.dto.request.PageRequest;
import com.dongnebook.support.BookStub;
import com.dongnebook.support.DataClearExtension;
import com.dongnebook.support.DatabaseCleaner;
import com.dongnebook.support.LocationStub;
import com.dongnebook.support.MemberStub;
import com.github.gavlyukovskiy.boot.jdbc.decorator.DataSourceDecoratorAutoConfiguration;

@DataJpaTest(showSql = false)
@ImportAutoConfiguration(DataSourceDecoratorAutoConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({TestConfig.class, DatabaseCleaner.class})
@ExtendWith(DataClearExtension.class)
class MemberQueryRepositoryTest {

	@Autowired
	MemberQueryRepository memberQueryRepository;

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	RentalRepository rentalRepository;

	@Autowired
	BookCommandRepository bookCommandRepository;


	@PersistenceContext
	EntityManager entityManager;

	private Member member;
	private Book book;

	@BeforeEach
	void setUp(){
		Arrays.stream(MemberStub.values()).forEach(value -> {
			member = value.of();
			memberRepository.save(member);
		});

		Arrays.stream(BookStub.values()).forEach(value -> {
			book = value.of();
			bookCommandRepository.save(book);
		});
	}


	@Test
	@DisplayName("회원가입을 한다.")
	void create() {
	    // given
		Member member1 = Member.builder().userId("asdf")
			.avatarUrl("asdf")
			.nickname("asdf")
			.password("asdfasdfassf")
			.build();
		Member save = memberRepository.save(member1);
		// when
		Long memberId = save.getId();



		// then
		Member member = memberRepository.findById(memberId).orElseThrow();
		assertThat(member.getId()).isEqualTo(9L);
	}
	
	@Test
	@DisplayName("닉네임이 중복됐는지 확인한다.")
	void nicknameDuplicate() {
		//given
	    // when
	    // then
		assertThat(memberRepository.existsByNickname(member.getNickname())).isTrue();
	}

	@Test
	@DisplayName("아이디가 중복됐는지 확인한다.")
	void userIdDuplicate() {
		// given
	    // when
	    // then
		assertThat(memberRepository.existsByUserId(member.getUserId())).isTrue();
	}

	@Test
	@DisplayName("주변에 있는 상인들을 조회한다.")
	void getSectorMerchantCounts() {
		//given
		Location 봉천역 = LocationStub.봉천역.of();

		MerchantSearchRequest condition = new MerchantSearchRequest(봉천역.getLongitude(), 봉천역.getLatitude(),
			40, 40, null, 3);
		//when

		List<Double> lonRangeList = Location.lonRangeList(condition.getLongitude(), condition.getWidth(),
			condition.getLevel());

		List<Double> latRangeList = Location.latRangeList(condition.getLatitude(), condition.getHeight(),
			condition.getLevel());

		List<Location> nearByBookLocation = memberQueryRepository.getNearByMerchant(condition);
		//then
		assertThat(nearByBookLocation).hasSize(8);
	}

	@Test
	@DisplayName("봉천역 주변 1km 섹터 5의 책 목록을 가져온다.")
	void getAll() {
		//given
		Location 봉천역 = LocationStub.봉천역.of();
		MerchantSearchRequest condition = new MerchantSearchRequest(봉천역.getLongitude(), 봉천역.getLatitude(),
			10, 10, 5, 3);


		List<Double> lonRangeList = Location.lonRangeList(condition.getLongitude(), condition.getWidth(),
			condition.getLevel());
		List<Double> latRangeList = Location.latRangeList(condition.getLatitude(), condition.getHeight(),
			condition.getLevel());
		//when
		SliceImpl<MemberResponse> content = memberQueryRepository.getAll(latRangeList, lonRangeList, condition,
			new PageRequest(null));

		assertThat(content.getContent()).hasSize(6);

		assertThat(content.hasNext()).isTrue();
		assertThat(content.isLast()).isFalse();


	}


}