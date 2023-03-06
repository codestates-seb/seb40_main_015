package com.dongnebook.domain.member.dto.response;

import com.dongnebook.domain.model.Location;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberResponse {
	private final Long merchantId;
	private final String merchantName;
	private final Location location;

	@QueryProjection
	@Builder
	public MemberResponse(Long merchantId, String merchantName, Location location) {
		this.merchantId = merchantId;
		this.merchantName = merchantName;
		this.location = location;
	}
}
