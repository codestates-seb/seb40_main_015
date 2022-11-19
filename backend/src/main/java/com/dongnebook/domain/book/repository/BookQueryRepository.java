package com.dongnebook.domain.book.repository;


import com.dongnebook.domain.book.dto.request.BookSearchCondition;


import static com.dongnebook.domain.book.domain.QBook.*;
import static com.dongnebook.domain.dibs.domain.QDibs.*;

import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;




import com.dongnebook.domain.book.dto.response.BookDetailResponse;

import com.dongnebook.domain.book.dto.response.BookSimpleResponse;
import com.dongnebook.domain.book.dto.response.QBookDetailResponse;
import com.dongnebook.domain.book.dto.response.QBookResponse;

import com.dongnebook.domain.book.dto.response.QBookSimpleResponse;
import com.dongnebook.domain.dibs.domain.Dibs;
import com.dongnebook.domain.dibs.domain.QDibs;
import com.dongnebook.domain.member.dto.response.QBookDetailMemberResponse;
import com.dongnebook.domain.model.Location;

import com.dongnebook.global.dto.request.PageRequest;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;



import lombok.extern.slf4j.Slf4j;


@Slf4j
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
			.where(book.id.eq(id))
			.fetchOne();

	}

	/**
	 * 위도 0.00001 = 1.1m 0.00227 = 250m
	 * 경도 0.00001 = 0.9m 0.00277 = 250m
	 * 보여주고 싶은 정보 -> 현재 위치가 중앙인 가로 세로 500m 짜리 정사각형에서 마커를 클러스터링 해서 보여주기
	 * db 에서 근방에 있는 책들 (pk와 주소) 모두 가져오기  -> 어플리케이션단에서 그룹핑하기
	 * db 에서 그룹핑해서 가져오기 -> 그럼 select 쿼리가 9번??
	 */
	public List<Location> getSectorBookCounts(BookSearchCondition bookSearchCondition) {

		String bookTitle = bookSearchCondition.getBookTitle();
		List<Double> LatRange = bookSearchCondition.latRangeList();
		List<Double> LonRange = bookSearchCondition.lonRangeList();

		return jpaQueryFactory.select(book.location)
			.from(book)
			.where(book.location.latitude.between(LatRange.get(3), LatRange.get(0))
				.and(book.location.longitude.between(LonRange.get(0), LonRange.get(3))
					.and(contains(bookTitle))))
			.fetch();
	}

	public SliceImpl<BookSimpleResponse> getAll(BookSearchCondition bookSearchCondition,
		PageRequest pageRequest) {

		String bookTitle = bookSearchCondition.getBookTitle();
		log.info("bookTitle = {}", bookTitle);
		List<Double> LatRange = bookSearchCondition.latRangeList();
		List<Double> LonRange = bookSearchCondition.lonRangeList();
		Integer sector = bookSearchCondition.getSector();


		List<BookSimpleResponse> result = jpaQueryFactory.select(
				new QBookSimpleResponse(book.id, book.title, book.bookState, book.ImgUrl, book.rentalFee, book.member.nickname))
			.from(book)
			.innerJoin(book.member)
			.where(ltBookId(pageRequest.getIndex())
				,(contains(bookTitle))
				,(sectorBetween(LatRange,LonRange,sector)))
			.orderBy(book.id.desc())
			.limit(pageRequest.getSize() + 1)
			.fetch();

		boolean hasNext = false;

		if (result.size() > pageRequest.getSize()) {
			hasNext = true;
			result.remove(pageRequest.getSize().intValue());
		}

		return new SliceImpl<>(result, pageRequest.of(), hasNext);
	}

	public SliceImpl<BookSimpleResponse> getAllDibsBook(Long memberId,
		PageRequest pageRequest) {

		List<BookSimpleResponse> result = jpaQueryFactory.select(
				new QBookSimpleResponse(dibs.book.id, dibs.book.title, dibs.book.bookState, dibs.book.ImgUrl, dibs.book.rentalFee, dibs.book.member.nickname))
			.from(dibs)
			.leftJoin(dibs.book)
			.leftJoin(dibs.book.member)
			.where(ltBookId(pageRequest.getIndex()),dibs.book.member.id.eq(memberId))
			.orderBy(dibs.book.id.desc())
			.limit(pageRequest.getSize() + 1)
			.fetch();

		boolean hasNext = false;

		if (result.size() > pageRequest.getSize()) {
			hasNext = true;
			result.remove(pageRequest.getSize().intValue());
		}

		return new SliceImpl<>(result, pageRequest.of(), hasNext);
	}

	private BooleanExpression contains(String bookTitle) {
		if(bookTitle.isBlank()){
			return null;
		}
		return book.title.contains(bookTitle);
	}

	private BooleanExpression ltBookId(Long bookId) {
		if (bookId == null) {
			return null;
		}
		return book.id.lt(bookId);
	}

	private BooleanExpression sectorBetween(List<Double> latRangeList, List<Double> lonRangeList,Integer givenSector) {

		if (Objects.isNull(latRangeList)||Objects.isNull(lonRangeList)||Objects.isNull(givenSector)) {
			return null;
		}

		int sector = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				sector++;
				if (givenSector == sector) {
					return book.location.latitude.between(latRangeList.get(i + 1), latRangeList.get(i))
						.and(book.location.longitude.between(lonRangeList.get(j), lonRangeList.get(j + 1)));
				}
			}
		}
		return null;
	}
}
