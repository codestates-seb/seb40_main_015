package com.dongnebook.domain.member.dto.Request;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberRegisterRequest {

	@NotBlank
	private String id;

	@NotBlank
	private String password;

	@NotBlank
	private String name;

	@Builder
	public MemberRegisterRequest(String id, String password, String name) {
		this.id = id;
		this.name = name;
		this.password = password;
	}
}
