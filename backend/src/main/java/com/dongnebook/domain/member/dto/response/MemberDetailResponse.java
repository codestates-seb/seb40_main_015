package com.dongnebook.domain.member.dto.response;

import com.dongnebook.domain.model.Location;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class MemberDetailResponse {
	private Long memberId;
	private String name;
	private Location location;
	private String address;
	private Integer totalBookCount;
	private String avatarUrl;
	private Double avgGrade;


	@QueryProjection
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

