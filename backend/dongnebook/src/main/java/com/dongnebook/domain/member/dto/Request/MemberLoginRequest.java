package com.dongnebook.domain.member.dto.Request;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberLoginRequest {

	@NotBlank
	private String userId;

	@NotBlank
	private String password;

	@Builder
	public MemberLoginRequest(String userId, String password) {
		this.userId = userId;
		this.password = password;
	}
}
