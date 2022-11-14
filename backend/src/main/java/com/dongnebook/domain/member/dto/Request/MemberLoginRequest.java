package com.dongnebook.domain.member.dto.Request;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberLoginRequest {

	@NotBlank
	private String id;

	@NotBlank
	private String password;

	@Builder
	public MemberLoginRequest(String id, String password) {
		this.id = id;
		this.password = password;
	}
}
