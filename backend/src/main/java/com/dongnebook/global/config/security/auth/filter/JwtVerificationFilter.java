package com.dongnebook.global.config.security.auth.filter;

import com.dongnebook.global.config.security.auth.exception.AccessTokenNotFound;
import com.dongnebook.global.error.ErrorResponse;
import com.dongnebook.global.error.exception.BusinessException;
import com.dongnebook.global.utils.CustomAuthorityUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class JwtVerificationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    private final TokenProvider tokenProvider;


    // 인증에서 제외할 url
    private static final List<String> EXCLUDE_URL =
        List.of("/auth/signup");

    /* 실제 필터링 로직은 doFilterInternal 에서 수행
	JWT 토큰의 인증 정보를 현재 쓰레드의 SecurityContext 에 저장하는 역할
	가입/로그인/재발급을 제외한 Request 요청은 모두 이 필터를 거치게 됨
	 */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        try {
            String jwt = resolveToken(request);
            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                // 토큰으로부터 Authentication 객체를 만듬
                Authentication authentication = tokenProvider.getAuthentication(jwt);
                // SecurityContext 에 Authentication 객체를 저장
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        } catch (RuntimeException e) {

            if (e instanceof BusinessException) {
                ObjectMapper objectMapper = new ObjectMapper();
                String json = objectMapper.writeValueAsString(ErrorResponse.of(((BusinessException)e).getErrorCode()));
                response.getWriter().write(json);
                response.setStatus(((BusinessException)e).getErrorCode().getStatus());

            }

        }

    }

    // Request Header 에서 토큰 정보를 꺼내오는 메소드
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }

        return null;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return EXCLUDE_URL.stream().anyMatch(exclude -> exclude.equalsIgnoreCase(request.getServletPath()));
    }

}
