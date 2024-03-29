package com.dongnebook.config;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.dongnebook.domain.alarm.repository.AlarmQueryRepository;
import com.dongnebook.domain.book.adapter.out.BookQueryRepository;
import com.dongnebook.domain.chat.repository.ChatRepository;
import com.dongnebook.domain.member.repository.MemberQueryRepository;
import com.dongnebook.domain.rental.repository.RentalQueryRepository;
import com.dongnebook.domain.reservation.repository.ReservationQueryRepository;
import com.dongnebook.domain.review.repository.ReviewQueryRepository;
import com.dongnebook.global.config.Py6spyConfig;
import com.p6spy.engine.spy.P6SpyOptions;
import com.querydsl.jpa.impl.JPAQueryFactory;

@TestConfiguration

public class TestConfig {

	@PersistenceContext
	private EntityManager entityManager;

	@Bean
	public JPAQueryFactory jpaQueryFactory() {
		return new JPAQueryFactory(entityManager);
	}

	@PostConstruct
	public void initTestConfig() {
		// P6Spy 로그 메시지 포맷 설정

		P6SpyOptions.getActiveInstance().setLogMessageFormat(Py6spyConfig.class.getName());
	}

	@Bean
	public BookQueryRepository bookQueryRepository() {
		return new BookQueryRepository(jpaQueryFactory());
	}
	@Bean
	public MemberQueryRepository memberQueryRepository(){
		return new MemberQueryRepository(jpaQueryFactory());
	}
	@Bean
	public RentalQueryRepository rentalQueryRepository() {
		return new RentalQueryRepository(jpaQueryFactory());
	}
	@Bean
	public ChatRepository chatRepository(){return new ChatRepository(jpaQueryFactory(), entityManager);}
	@Bean
	public ReservationQueryRepository reservationQueryRepository() {
		return new ReservationQueryRepository(jpaQueryFactory());
	}

	@Bean
	public ReviewQueryRepository reviewQueryRepository() {
		return new ReviewQueryRepository(jpaQueryFactory());
	}

	@Bean
	public AlarmQueryRepository alarmQueryRepository() {
		return new AlarmQueryRepository(jpaQueryFactory(), entityManager);
	}

	@Bean(name = "taskExecutor")
	public TaskExecutor clientInboundChannelExecutor() {
		// ...
		return new ThreadPoolTaskExecutor();
	}



}