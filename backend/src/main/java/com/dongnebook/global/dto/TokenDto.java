package com.dongnebook.global.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenDto {
	private String grantType;
	private String accessToken;
	private String refreshToken;
	private Long accessTokenExpiresIn;
}
