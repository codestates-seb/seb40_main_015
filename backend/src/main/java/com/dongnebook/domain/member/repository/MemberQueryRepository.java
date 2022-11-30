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
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepository {
	private final JPAQueryFactory jpaQueryFactory;

	public List<Location> getSectorMerchantCounts(MerchantSearchRequest request) {

		List<Double> LatRange = Location.latRangeList(request.getLatitude(), request.getHeight(), request.getLevel());
		List<Double> LonRange = Location.lonRangeList(request.getLongitude(), request.getWidth(), request.getLevel());

		return jpaQueryFactory.select(member.location)
			.from(member)
			.where((member.location.latitude.between(LatRange.get(request.getLevel()), LatRange.get(0))),
				(member.location.longitude.between(LonRange.get(0), LonRange.get(request.getLevel()))))
			.fetch();

	}

	public SliceImpl<MemberResponse> getAll(MerchantSearchRequest request, PageRequest pageRequest) {

		List<Double> LatRange = Location.latRangeList(request.getLatitude(), request.getHeight(), request.getLevel());
		List<Double> LonRange = Location.lonRangeList(request.getLongitude(), request.getWidth(), request.getLevel());

		List<MemberResponse> result = jpaQueryFactory.select(new QMemberResponse(member.id, member.nickname,member.location))
			.from(member)
			.where((member.location.latitude.between(LatRange.get(request.getLevel()), LatRange.get(0))),
				(member.location.longitude.between(LonRange.get(0), LonRange.get(request.getLevel()))),
				ltMemberId(pageRequest.getIndex()),
				request.sectorBetween())
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
	public Optional<Member> findByMemberWithRental(Long memberId){
		return Optional.ofNullable(jpaQueryFactory.select(member)
			.from(member)
			.leftJoin(member.bookList, book).fetchJoin()
			.where(member.id.eq(memberId))
			.fetchFirst());
	}


	public  MemberDetailResponse getMyInfo(Long memberId){

		return jpaQueryFactory.select(
				new QMemberDetailResponse(member.id, member.nickname, member.location, member.address, member.bookList.size(),
					member.avatarUrl, member.avgGrade))
			.from(member)
			.where(member.id.eq(memberId))
			.fetchOne();
	}


	private BooleanExpression ltMemberId(Long memberId) {
		if (memberId == null) {
			return null;
		}
		return member.id.lt(memberId);
	}

}
