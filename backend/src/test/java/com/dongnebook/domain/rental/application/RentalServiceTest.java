package com.dongnebook.domain.rental.application;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.book.dto.request.BookRegisterRequest;
import com.dongnebook.domain.book.repository.BookCommandRepository;
import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.member.dto.request.MemberEditRequest;
import com.dongnebook.domain.member.repository.MemberRepository;
import com.dongnebook.domain.model.Location;
import com.dongnebook.domain.rental.domain.Rental;
import com.dongnebook.domain.rental.repository.RentalRepository;
import com.dongnebook.domain.rental.ui.RentalController;

import lombok.extern.slf4j.Slf4j;

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

	@Test
	void createRental() throws InterruptedException {
		int num = 4;
		List<Member> members = makeMembers(num);
		makeBooks(num, members);
		ExecutorService executorService = Executors.newFixedThreadPool(num);
		CountDownLatch countDownLatch = new CountDownLatch(num);
		for (int i = 2; i < num; i++) {
			int finalI = i;
			executorService.execute(
				() -> {
					rentalController.postRental(1L, (long)finalI);
					countDownLatch.countDown();
				});

		}
		countDownLatch.await(4, TimeUnit.SECONDS);

		List<Rental> all = rentalRepository.findAll();
		int count = (int)all.stream().filter(rental -> rental.getBook().getId() == 1L).count();
		Assertions.assertEquals(1L,count);
	}

	private void makeBooks(int num, List<Member> members) {
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
	}

	private List<Member> makeMembers(int num) {
		List<Member> members = new ArrayList<>();
		for (int i = 0; i < num; i++) {
			Member member = makeMember(Location.builder()
				.latitude(37.4831 + ((double)i / num))
				.longitude(126.9438 + ((double)i / num))
				.build(), i);
			members.add(member);
			memberRepository.save(member);
		}
		return members;
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