package com.dongnebook.domain.book.application.port.in.response;

import java.io.Serializable;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookDetailMemberResponse implements Serializable {
	private Long merchantId;
	private String name;
	private Double grade;
	private String avatarUrl;

	@QueryProjection
	@Builder
	public BookDetailMemberResponse(Long merchantId, String name, Double grade, String avatarUrl) {
		this.merchantId = merchantId;
		this.name = name;
		this.grade = grade;
		this.avatarUrl = avatarUrl;
	}

}
