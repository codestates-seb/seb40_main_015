package com.dongnebook.global.security.auth.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.dongnebook.domain.refreshtoken.domain.RefreshToken;
import com.dongnebook.domain.refreshtoken.repository.RefreshTokenRepository;
import com.dongnebook.global.security.auth.userdetails.AuthMember;

import io.jsonwebtoken.io.IOException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MemberAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private final RefreshTokenRepository refreshTokenRepository;

	public MemberAuthenticationSuccessHandler(RefreshTokenRepository refreshTokenRepository) {
		this.refreshTokenRepository = refreshTokenRepository;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException {
		AuthMember authMember = (AuthMember)authentication.getPrincipal();
		String refreshToken = response.getHeader("Set-Cookie").substring(13).split(";")[0];

		// RefreshToken 저장
		RefreshToken refresh = RefreshToken.builder().key(authMember.getMemberId()).value(refreshToken).build();

		refreshTokenRepository.save(refresh);
	}
}

