package com.dongnebook.domain.member.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberRegisterRequest {

	@NotBlank(message = "아이디는 필수 입력 값입니다.")
	@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "아이디는 영문과 숫자의 조합입니다.")
	private String userId;

	@NotBlank(message = "비밀번호는 필수 입력 값입니다.")
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$", message = "비밀번호는 영문과 숫자, 특수문자를 포함해 8자리 이상이어야 합니다.")
	private String password;

	@NotBlank(message = "닉네임은 필수 입력 값입니다.")
	private String nickname;

	@Builder
	public MemberRegisterRequest(String userId, String password, String nickname) {
		this.userId = userId;
		this.nickname = nickname;
		this.password = password;
	}

}