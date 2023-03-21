package com.dongnebook.domain.book.adapter.out;

import static com.dongnebook.domain.book.domain.QBook.*;
import static com.dongnebook.domain.dibs.domain.QDibs.*;
import static com.dongnebook.domain.rental.domain.QRental.*;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import com.dongnebook.domain.book.application.port.in.request.BookSearchCommand;
import com.dongnebook.domain.book.application.port.in.request.PageRequest;
import com.dongnebook.domain.book.application.port.in.response.BookDetailResponse;
import com.dongnebook.domain.book.application.port.in.response.BookSimpleResponse;
import com.dongnebook.domain.book.application.port.in.response.QBookDetailResponse;
import com.dongnebook.domain.book.application.port.in.response.QBookResponse;
import com.dongnebook.domain.book.application.port.in.response.QBookSimpleResponse;
import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.book.domain.BookState;
import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.member.dto.response.QBookDetailMemberResponse;
import com.dongnebook.domain.model.Location;
import com.dongnebook.domain.rental.domain.RentalState;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BookQueryRepository {
	private final JPAQueryFactory jpaQueryFactory;
	List<Double> latRange;
	List<Double> lonRange;


	public Optional<Book> findWithMerchantByBookId(Long bookId) {
		return Optional.ofNullable(jpaQueryFactory
			.select(book)
			.from(book)
			.innerJoin(book.member)
			.fetchJoin()
			.where(book.id.eq(bookId))
			.fetchFirst());
	}

	public Optional<BookDetailResponse> findBookDetail(Long bookId, Long memberId) {
		return Optional.ofNullable(jpaQueryFactory
			.select(new QBookDetailResponse(
				new QBookResponse(
					book.id,
					book.bookProduct.title,
					book.bookProduct.author,
					book.bookProduct.publisher,
					book.rentalFee.value,
					book.description,
					book.bookState,
					book.imgUrl,
					dibsCountSubQuery(bookId, memberId),
					rental.rentalStartedAt,
					rental.rentalDeadLine),
				new QBookDetailMemberResponse(
					book.member.id,
					book.member.nickname,
					book.member.avgGrade,
					book.member.avatarUrl)))
			.from(book)
			.leftJoin(book.member)
			.leftJoin(rental)
			.on(rental.book.id.eq(bookId), rental.rentalState.eq(RentalState.BEING_RENTED))
			.leftJoin(book.dibsList, dibs)
			.where(book.id.eq(bookId))
			.fetchFirst());
		//책을 찾는데 찜이 하나라도 있으면 그 찜의 아이디를 찾아서 반환 찜이 없으면 걍 넘어감
	}


	/**
	 * 위도 0.00001 = 1.1m 0.00227 = 250m
	 * 경도 0.00001 = 0.9m 0.00277 = 250m
	 * 보여주고 싶은 정보 -> 현재 위치가 중앙인 가로 세로 500m 짜리 정사각형에서 마커를 클러스터링 해서 보여주기
	 * db 에서 근방에 있는 책들 (pk와 주소) 모두 가져오기  -> 어플리케이션단에서 그룹핑하기
	 * db 에서 그룹핑해서 가져오기 -> 그럼 select 쿼리가 9번??
	 */
	public List<Location> getNearByBookLocation(BookSearchCommand condition) {
		String bookTitle = condition.getBookTitle();
		this.latRange = Location.latRangeList(condition.getLatitude(), condition.getHeight(),
			condition.getLevel());
		this.lonRange = Location.lonRangeList(condition.getLongitude(), condition.getWidth(),
			condition.getLevel());

		return jpaQueryFactory
			.select(book.location)
			.from(book)
			.where(book.location.latitude.between(this.latRange.get(this.latRange.size() - 1), this.latRange.get(0)),
				book.location.longitude.between(this.lonRange.get(0), this.lonRange.get(this.lonRange.size() - 1)),
				contains(bookTitle), bookToShow())
			.fetch();
	}

	public SliceImpl<BookSimpleResponse> getAll(BookSearchCommand condition, PageRequest pageRequestImpl) {
		boolean hasNext = false;
		String bookTitle = condition.getBookTitle();

		List<BookSimpleResponse> result = jpaQueryFactory
			.select(new QBookSimpleResponse(
				book.id,
				book.bookProduct.title,
				book.bookState,
				book.imgUrl,
				book.rentalFee,
				book.location,
				book.member.nickname))
			.from(book)
			.innerJoin(book.member)
			.where(ltBookId(pageRequestImpl.getIndex()),
				contains(bookTitle),
				Location.inSector(condition.getLatitude(), condition.getLongitude(), condition.getHeight(),
					condition.getWidth(), condition.getSector(), condition.getLevel(), book.location),
				bookToShow())
			.orderBy(book.id.desc())
			.limit(pageRequestImpl.getSize() + 1)
			.fetch();

		if (result.size() > pageRequestImpl.getSize()) {
			hasNext = true;
			result.remove(pageRequestImpl.getSize().intValue());
		}

		return new SliceImpl<>(result, pageRequestImpl.of(), hasNext);
	}

	public SliceImpl<BookSimpleResponse> getAllDibsBook(Long memberId, PageRequest pageRequestImpl) {
		List<BookSimpleResponse> result = jpaQueryFactory
			.select(
				new QBookSimpleResponse(
					dibs.book.id,
					dibs.book.bookProduct.title,
					dibs.book.bookState,
					dibs.book.imgUrl,
					dibs.book.rentalFee,
					dibs.book.location,
					dibs.book.member.nickname))
			.from(dibs)
			.leftJoin(dibs.book, book)
			.leftJoin(dibs.book.member)
			.where(ltDibsBookId(pageRequestImpl.getIndex()), dibs.member.id.eq(memberId), bookToShow())
			.orderBy(dibs.book.id.desc())
			.limit(pageRequestImpl.getSize() + 1)
			.fetch();

		boolean hasNext = false;

		if (result.size() > pageRequestImpl.getSize()) {
			hasNext = true;
			result.remove(pageRequestImpl.getSize().intValue());
		}

		return new SliceImpl<>(result, pageRequestImpl.of(), hasNext);
	}

	public SliceImpl<BookSimpleResponse> getListByMember(Long memberId, PageRequest pageRequestImpl) {
		List<BookSimpleResponse> result = jpaQueryFactory
			.select(
				new QBookSimpleResponse(
					book.id,
					book.bookProduct.title,
					book.bookState,
					book.imgUrl))
			.from(book)
			.where(ltBookId(pageRequestImpl.getIndex()),
				book.member.id.eq(memberId),
				bookToShow())
			.orderBy(book.id.desc())
			.limit(pageRequestImpl.getSize() + 1)
			.fetch();

		boolean hasNext = false;

		if (result.size() > pageRequestImpl.getSize()) {
			hasNext = true;
			result.remove(pageRequestImpl.getSize().intValue());
		}

		return new SliceImpl<>(result, pageRequestImpl.of(), hasNext);
	}

	private BooleanExpression contains(String bookTitle) {
		if (bookTitle.isBlank()) {
			return null;
		}

		return book.bookProduct.title.contains(bookTitle);
	}

	private Expression<Long> dibsCountSubQuery(Long bookId, Long memberId) {
		if (memberId == null) {
			return Expressions.ZERO.longValue();
		}

		return ExpressionUtils.as(JPAExpressions
			.select(dibs.id.count())
			.from(dibs)
			.where(dibs.book.id.eq(bookId),
				dibs.member.id.eq(memberId)),
			"count");
	}

	private BooleanExpression ltDibsBookId(Long bookId) {
		if (bookId == null) {
			return null;
		}

		return dibs.book.id.lt(bookId);
	}

	private BooleanExpression ltBookId(Long bookId) {
		if (bookId == null) {
			return null;
		}

		return book.id.lt(bookId);
	}

	private BooleanExpression bookToShow() {
		return book.bookState.ne(BookState.DELETED).and(book.bookState.ne(BookState.TRADING));
	}

	public void updateBookLocation(Member member, Location location) {
		jpaQueryFactory.update(book).set(book.location, location).where(book.member.id.eq(member.getId())).execute();
	}
}
