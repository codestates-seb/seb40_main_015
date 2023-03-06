package com.dongnebook.domain.member.repository;

import static com.dongnebook.domain.book.domain.QBook.*;
import static com.dongnebook.domain.member.domain.QMember.*;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import com.dongnebook.domain.book.domain.BookState;
import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.member.dto.request.MerchantSearchRequest;

import com.dongnebook.domain.member.dto.response.MemberResponse;

import com.dongnebook.domain.member.dto.response.MemberDetailResponse;
import com.dongnebook.domain.member.dto.response.QMemberDetailResponse;

import com.dongnebook.domain.member.dto.response.QMemberResponse;
import com.dongnebook.domain.model.Location;
import com.dongnebook.global.dto.request.PageRequest;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepository {
	private final JPAQueryFactory jpaQueryFactory;
	List<Double> latRange;
	List<Double> lonRange;

	public List<Location> getNearByMerchant(MerchantSearchRequest request) {
		this.latRange = Location.latRangeList(request.getLatitude(), request.getHeight(), request.getLevel());
		this.lonRange = Location.lonRangeList(request.getLongitude(), request.getWidth(), request.getLevel());

		return jpaQueryFactory.select(member.location)
			.from(member)
			.where((member.location.latitude.between(latRange.get(request.getLevel()), latRange.get(0))),
				(member.location.longitude.between(lonRange.get(0), lonRange.get(request.getLevel()))))
			.fetch();
	}

	public SliceImpl<MemberResponse> getAll(List<Double> latRangeList, List<Double> lonRangeList,
		MerchantSearchRequest request, PageRequest pageRequest) {
		List<MemberResponse> result = jpaQueryFactory
			.select(
				new QMemberResponse(
					member.id,
					member.nickname,
					member.location))
			.from(member)
			.where((member.location.latitude.between(latRangeList.get(request.getLevel()), latRangeList.get(0))),
				(member.location.longitude.between(lonRangeList.get(0), lonRangeList.get(request.getLevel()))),
				ltMemberId(pageRequest.getIndex()),
				Location.inSector(request.getLatitude(), request.getLongitude(), request.getHeight(),
					request.getWidth(),
					request.getSector(), request.getLevel(), member.location))
			.orderBy(member.id.desc())
			.limit(pageRequest.getSize() + 1)
			.fetch();

		boolean hasNext = false;

		if (result.size() > pageRequest.getSize()) {
			hasNext = true;
			result.remove(pageRequest.getSize().intValue());
		}

		return new SliceImpl<>(result, pageRequest.of(), hasNext);
	}

	//대여중인 책이 없는 회원만 가져온다.
	public Optional<Member> findByMemberWithRental(Long memberId) {
		return Optional.ofNullable(jpaQueryFactory.select(member)
			.from(member)
			.leftJoin(member.bookList, book).fetchJoin()
			.where(member.id.eq(memberId))
			.fetchFirst());
	}

	public Optional<MemberDetailResponse> findMyInfo(Long memberId) {
		Expression<Integer> totalBookCount = ExpressionUtils.as(JPAExpressions
			.select(book.id.count().intValue())
			.from(book)
			.where(book.member.id.eq(memberId), book.bookState.ne(BookState.DELETED)), "totalBookCount");
		return Optional.ofNullable(jpaQueryFactory
			.select(
				new QMemberDetailResponse(
					member.id,
					member.nickname,
					member.location,
					member.address,
					totalBookCount,
					member.avatarUrl,
					member.avgGrade))
			.from(member)
			.where(member.id.eq(memberId))
			.fetchOne());
	}

	private BooleanExpression ltMemberId(Long memberId) {
		if (memberId == null) {
			return null;
		}

		return member.id.lt(memberId);
	}
}
