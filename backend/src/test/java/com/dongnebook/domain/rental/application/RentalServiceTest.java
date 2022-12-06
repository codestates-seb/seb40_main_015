package com.dongnebook.domain.rental.application;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.dongnebook.domain.book.application.BookService;
import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.book.domain.BookState;
import com.dongnebook.domain.book.dto.request.BookRegisterRequest;
import com.dongnebook.domain.book.repository.BookCommandRepository;
import com.dongnebook.domain.member.application.MemberService;
import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.member.dto.request.MemberEditRequest;
import com.dongnebook.domain.member.dto.request.MemberRegisterRequest;
import com.dongnebook.domain.member.repository.MemberRepository;
import com.dongnebook.domain.model.Location;
import com.dongnebook.domain.rental.domain.Rental;
import com.dongnebook.domain.rental.repository.RentalRepository;
import com.dongnebook.domain.rental.ui.RentalController;
import com.dongnebook.global.config.security.auth.userdetails.AuthMember;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class RentalServiceTest {

	@Autowired
	RentalService rentalService;

	@Autowired
	RentalRepository rentalRepository;

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	BookCommandRepository bookCommandRepository;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	EntityManager entityManager;

	@Autowired
	RentalController rentalController;

	@BeforeEach
	public void inser() {
		int num = 12;
		ArrayList<Member> members = new ArrayList<>();
		for (int i = 0; i < num; i++) {
			Member member = makeMember(Location.builder()
				.latitude(37.4831 + ((double)i / num))
				.longitude(126.9438 + ((double)i / num))
				.build(), i);
			members.add(member);
			memberRepository.save(member);
		}

		for (Member member : memberRepository.findAll()) {
			System.out.println(member.getId());
		}

		for (int i = 0; i < num; i++) {
			bookCommandRepository.save(Book.create(BookRegisterRequest.builder().author("asdf" + i)
				.description("asdf")
				.imageUrl("asdf" + i)
				.publisher("Asdf" + i)
				.rentalFee(i)
				.title("asdf" + i)
				.build(), Location.builder()
				.latitude(37.4831 + ((double)i / num))
				.longitude(126.9438 + ((double)i / num))
				.build(), members.get(i)));
		}

		for (Book book : bookCommandRepository.findAll()) {
			System.out.println(book.getId());
		}

	}

	// @Test
	void createRental() throws InterruptedException {
		for (Member member : memberRepository.findAll()) {
			System.out.println(member.getId());
		}
		int num2 = 8;
		ExecutorService executorService = Executors.newFixedThreadPool(num2);
		CountDownLatch countDownLatch = new CountDownLatch(num2);
		for (int i = 0; i < num2; i++) {
			int finalI = i;
			executorService.execute(
				() -> {
					rentalController.postRental(1L, AuthMember.of((long)finalI + 2L, List.of("member")));
					countDownLatch.countDown();
				});
		}

		countDownLatch.await();

		List<Rental> all = rentalRepository.findAll();
		Assertions.assertEquals(all.size(), 1L);
	}

	private Member makeMember(Location location, int i) {
		Member build = Member.builder()
			.avatarUrl("asdf" + i)
			.userId("thwn" + i)
			.password("asdfasdf" + i)
			.nickname("test" + i)
			.build();
		build.edit(MemberEditRequest.builder().
			address("서울시 땡땡구 땡땡동")
			.avatarUrl(build.getAvatarUrl())
			.nickname(build.getNickname())
			.location(location)
			.build());

		return build;
	}
}