package com.dongnebook.domain.member.dto.response;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class BookDetailMemberResponse {
	private String name;
	private Long grade;

	@QueryProjection
	public BookDetailMemberResponse(String name, Long grade) {
		this.name = name;
		this.grade = grade;
	}

}
