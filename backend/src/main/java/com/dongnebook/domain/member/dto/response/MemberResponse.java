package com.dongnebook.domain.member.dto.response;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class MemberResponse {

	private Long id;
	private String name;

	@QueryProjection
	public MemberResponse(Long id, String name) {
		this.id = id;
		this.name = name;
	}
}
