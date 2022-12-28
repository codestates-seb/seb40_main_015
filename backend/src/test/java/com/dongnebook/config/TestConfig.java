package com.dongnebook.config;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.dongnebook.domain.book.repository.BookQueryRepository;
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


}