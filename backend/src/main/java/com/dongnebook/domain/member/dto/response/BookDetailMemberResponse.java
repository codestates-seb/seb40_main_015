package com.dongnebook.domain.member.dto.response;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class BookDetailMemberResponse {
	private Long merchantId;
	private String name;
	private Long grade;
	private String avatarUrl;

	@QueryProjection
	public BookDetailMemberResponse(Long merchantId, String name, Long grade, String avatarUrl) {
		this.merchantId = merchantId;
		this.name = name;
		this.grade = grade;
		this.avatarUrl = avatarUrl;
	}

}
