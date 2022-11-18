package com.dongnebook.global.config.security.auth.handler;

import io.jsonwebtoken.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dongnebook.global.config.security.auth.userdetails.AuthMember;

@Slf4j
public class MemberAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        AuthMember authMember = (AuthMember) authentication.getPrincipal();

        String refreshToken = response.getHeader("Set-Cookie").substring(13).split(";")[0];

        // RefreshToken 저장
        // RefreshToken refresh = RefreshToken.builder()
        //     .key(authMember.getMemberId())
        //     .value(refreshToken)
        //     .build();
        //
        // refreshTokenRepository.save(refresh);
    }
    }

