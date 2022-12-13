package com.dongnebook.global.security.auth.filter;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.dongnebook.domain.member.exception.UnAuthorizedException;
import com.dongnebook.domain.refreshtoken.exception.TokenEmpty;
import com.dongnebook.domain.refreshtoken.exception.TokenExpired;
import com.dongnebook.domain.refreshtoken.exception.TokenMalformed;
import com.dongnebook.domain.refreshtoken.exception.TokenUnsupported;
import com.dongnebook.global.dto.TokenDto;
import com.dongnebook.global.security.auth.userdetails.AuthMember;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TokenProvider {

	private static final String BEARER_TYPE = "bearer";
	private static final String ROLES = "roles";
	@Value("${jwt.access-token-expiration-minutes}")
	private int accessTokenExpireTime;
	@Value("${jwt.refresh-token-expiration-minutes}")
	private int refreshTokenExpireTime;
	private final Key key;

	public TokenProvider(@Value("${jwt.secret-key}") String secretKey) {
		byte[] keyBytes = Decoders.BASE64URL.decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}

	public Date getTokenExpiration(int expirationMinutes) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, expirationMinutes);
		return calendar.getTime();
	}

	public TokenDto generateTokenDto(AuthMember authMember) {
		// 권한들 가져오기
		String authorities = authMember.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.joining(","));

		Date accessTokenExpiresIn = getTokenExpiration(accessTokenExpireTime);
		Date refreshTokenExpiresIn = getTokenExpiration(refreshTokenExpireTime);

		Map<String, Object> claims = new HashMap<>();
		claims.put("id", authMember.getMemberId());
		claims.put(ROLES, authorities);

		// Access Token 생성
		String accessToken = Jwts.builder()
			.setSubject(authMember.getUserId())                  // payload "sub": "name"
			.setClaims(claims)      // payload "auth": "ROLE_USER"
			.setExpiration(accessTokenExpiresIn)                   // payload "exp": 1516239022 (예시)
			.signWith(key, SignatureAlgorithm.HS512)          // header "alg": "HS512"
			.compact();

		// Refresh Token 생성
		String refreshToken = Jwts.builder()
			.setSubject(authMember.getMemberId().toString())
			.setExpiration(refreshTokenExpiresIn)
			.signWith(key, SignatureAlgorithm.HS512)
			.compact();

		return TokenDto.builder()
			.grantType(BEARER_TYPE)
			.accessToken(accessToken)
			.accessTokenExpiresIn(accessTokenExpiresIn.getTime())
			.refreshToken(refreshToken)
			.build();
	}

	public Long getMemberId(String accessToken) {
		// 토큰 복호화
		Claims claims = parseClaims(accessToken);

		if (Objects.isNull(claims.get(ROLES))) {
			throw new UnAuthorizedException();
		}

		return claims.get("id", Long.class);
	}

	// 토큰 검증
	public boolean validateToken(String token) {
		try {
			parseClaims(token);
			return true;
		} catch (MalformedJwtException e) {
			log.info("Invalid JWT token");
			log.trace("Invalid JWT token trace: {}", e);
			throw new TokenMalformed();
		} catch (ExpiredJwtException e) {
			log.info("Expired JWT token");
			log.trace("Expired JWT token trace: {}", e);
			throw new TokenExpired();
		} catch (UnsupportedJwtException e) {
			log.info("Unsupported JWT token");
			log.trace("Unsupported JWT token trace: {}", e);
			throw new TokenUnsupported();
		} catch (IllegalArgumentException e) {
			log.info("JWT claims string is empty.");
			log.trace("JWT claims string is empty trace: {}", e);
			throw new TokenEmpty();
		}
	}

	public Claims parseClaims(String accessToken)  {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
	}
}
