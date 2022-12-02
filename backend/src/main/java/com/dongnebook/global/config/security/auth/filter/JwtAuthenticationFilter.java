package com.dongnebook.global.config.security.auth.filter;


import com.dongnebook.domain.member.dto.request.MemberLoginRequest;
import com.dongnebook.domain.member.dto.response.MemberLoginResponse;
import com.dongnebook.domain.member.dto.response.MemberResponse;
import com.dongnebook.global.config.security.auth.userdetails.AuthMember;
import com.dongnebook.global.dto.TokenDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response) throws AuthenticationException {

        ObjectMapper om = new ObjectMapper();
        MemberLoginRequest loginRequest= om.readValue(request.getInputStream(), MemberLoginRequest.class);

        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(loginRequest.getUserId(), loginRequest.getPassword());

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
        HttpServletResponse response,
        FilterChain chain,
        Authentication authResult) throws IOException, ServletException {
        AuthMember authMember = (AuthMember) authResult.getPrincipal();
        TokenDto tokenDto = tokenProvider.generateTokenDto(authMember);

        String refreshToken = tokenDto.getRefreshToken();

        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
            .maxAge(7 * 24 * 60 * 60)
            .path("/")
            .secure(true)
            .sameSite("None")
            .httpOnly(true)
            .build();

        response.setHeader("Set-Cookie", cookie.toString());
        response.setHeader("Authorization", "Bearer " + tokenDto.getAccessToken());

        ObjectMapper objectMapper = new ObjectMapper();
        String memberLoginResponse = objectMapper.writeValueAsString(
            MemberLoginResponse.of(authMember)
        );
        // response body에 member의 id, username을 담아서 보내준다.
        response.getWriter().write(
            memberLoginResponse
        );

        this.getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
    }

}
