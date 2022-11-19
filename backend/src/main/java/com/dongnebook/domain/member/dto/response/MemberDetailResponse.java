package com.dongnebook.domain.member.dto.response;

import com.dongnebook.domain.model.Location;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class MemberDetailResponse {

	private Long memberId;
	private String name;
	private Location location;
	private Integer totalBookCount;
	private String avatarUrl;
	private Long avgGrade;

	@QueryProjection
	public MemberDetailResponse(Long memberId, String name, Location location, Integer totalBookCount, String avatarUrl,
		Long avgGrade) {
		this.memberId = memberId;
		this.name = name;
		this.location = location;
		this.totalBookCount = totalBookCount;
		this.avatarUrl = avatarUrl;
		this.avgGrade = avgGrade;
	}
}

