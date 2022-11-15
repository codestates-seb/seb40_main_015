package com.dongnebook.domain.book.repository;

import static com.dongnebook.domain.book.domain.QBook.*;
import static com.dongnebook.domain.member.domain.QMember.*;

import org.springframework.stereotype.Repository;

import com.dongnebook.domain.book.dto.response.BookDetailResponse;
import com.dongnebook.domain.book.dto.response.QBookDetailResponse;
import com.dongnebook.domain.book.dto.response.QBookResponse;
import com.dongnebook.domain.member.dto.response.QBookDetailMemberResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BookQueryRepository {

	private final JPAQueryFactory jpaQueryFactory;

	public BookDetailResponse getDetail(Long id) {

		return jpaQueryFactory.select(new QBookDetailResponse(
					new QBookResponse(
						book.id,
						book.title,
						book.publisher,
						book.rentalFee.value,
						book.description,
						book.bookState
					),
					new QBookDetailMemberResponse(
						book.member.nickname,
						book.member.avgGrade
					)
				)
			)
			.from(book)
			.leftJoin(book.member)
			.fetchOne();

	}

}
