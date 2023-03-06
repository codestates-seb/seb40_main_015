package com.dongnebook.domain.member.dto.response;

import com.dongnebook.domain.model.Location;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberDetailResponse {
	private final Long memberId;
	private final String name;
	private final Location location;
	private final String address;
	private final Integer totalBookCount;
	private final String avatarUrl;
	private final Double avgGrade;


	@QueryProjection
	@Builder
	public MemberDetailResponse(Long memberId, String name, Location location, String address, Integer totalBookCount, String avatarUrl,
		Double avgGrade) {
		this.memberId = memberId;
		this.name = name;
		this.location = location;
		this.address = address;
		this.totalBookCount = totalBookCount;
		this.avatarUrl = avatarUrl;
		this.avgGrade = avgGrade;
	}
}

