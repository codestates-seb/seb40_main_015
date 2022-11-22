package com.dongnebook.domain.member.dto.response;

import com.dongnebook.domain.model.Location;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberResponse {

	private Long id;
	private String name;


	@QueryProjection

	public MemberResponse(Long id, String name) {
		this.id = id;
		this.name = name;
	}
}
