package com.dongnebook.domain.member.dto.request;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
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