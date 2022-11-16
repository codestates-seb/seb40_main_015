package com.dongnebook.domain.book.repository;

import static com.dongnebook.domain.book.domain.QBook.*;

import java.util.List;

import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import com.dongnebook.domain.book.dto.request.SectorBookCountRequest;
import com.dongnebook.domain.book.dto.response.BookDetailResponse;

import com.dongnebook.domain.book.dto.response.BookSimpleResponse;
import com.dongnebook.domain.book.dto.response.QBookDetailResponse;
import com.dongnebook.domain.book.dto.response.QBookResponse;
import com.dongnebook.domain.book.dto.response.QBookSimpleResponse;
import com.dongnebook.domain.member.dto.response.QBookDetailMemberResponse;
import com.dongnebook.domain.model.Location;
import com.dongnebook.global.dto.request.PageRequest;
import com.querydsl.core.types.dsl.BooleanExpression;
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
			.innerJoin(book.member)
			.fetchOne();

	}

	/**
	 * 위도 0.00001 = 1.1m 0.00227 = 250m
	 * 경도 0.00001 = 0.9m 0.00277 = 250m
	 * 보여주고 싶은 정보 -> 현재 위치가 중앙인 가로 세로 500m 짜리 정사각형에서 마커를 클러스터링 해서 보여주기
	 * db 에서 근방에 있는 책들 (pk와 주소) 모두 가져오기  -> 어플리케이션단에서 그룹핑하기
	 * db 에서 그룹핑해서 가져오기 -> 그럼 select 쿼리가 9번??

	 */
	public List<Location> getSectorBookCounts(SectorBookCountRequest sectorBookCountRequest) {

		String bookTitle = sectorBookCountRequest.getBookTitle();
		List<Double> LatRange = sectorBookCountRequest.latRangeList();
		List<Double> LonRange = sectorBookCountRequest.lonRangeList();

		return jpaQueryFactory.select(book.location)
			.from(book)
			.where(book.location.latitude.between(LatRange.get(3), LatRange.get(0))
				.and(book.location.longitude.between(LonRange.get(0), LonRange.get(3))
					.and(book.title.contains(bookTitle))))
			.fetch();
	}

	public SliceImpl<BookSimpleResponse> noOffsetPagingList(PageRequest pageRequest) {
		List<BookSimpleResponse> result = jpaQueryFactory.select(
				new QBookSimpleResponse(book.id, book.title, book.bookState, book.ImgUrl, book.member.nickname))
			.from(book)
			.innerJoin(book.member)
			.where(ltBookId((long)pageRequest.getIndex()))
			.orderBy(book.id.desc())
			.limit(pageRequest.getSize() + 1)
			.fetch();

		boolean hasNext = false;

		if (result.size() > pageRequest.getSize()) {
			result.remove(pageRequest.getSize());
			hasNext = true;
		}

		return new SliceImpl<>(result, pageRequest.of(), hasNext);

	}

	private BooleanExpression ltBookId(Long bookId) {
		if(bookId == -1){
			return null;
		}
		return book.id.lt(bookId);
	}

}
