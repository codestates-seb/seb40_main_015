package com.dongnebook.global.config.security.auth.filter;

import java.security.Key;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.dongnebook.domain.refreshtoken.exception.TokenEmpty;
import com.dongnebook.domain.refreshtoken.exception.TokenExpired;
import com.dongnebook.domain.refreshtoken.exception.TokenMalformed;
import com.dongnebook.domain.refreshtoken.exception.TokenSignatureInvalid;
import com.dongnebook.domain.refreshtoken.exception.TokenUnsupported;
import com.dongnebook.global.config.security.auth.userdetails.AuthMember;
import com.dongnebook.global.dto.TokenDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TokenProvider {
	/* 유저 정보로 JWT 토큰을 만들거나 토큰을 바탕으로 유저 정보를 가져옴
	 *  JWT 토큰 관련 암호화, 복호화, 검증 로직
	 */

	private static final String BEARER_TYPE = "bearer";

	@Value("${jwt.access-token-expiration-minutes}")
	private int ACCESS_TOKEN_EXPIRE_TIME;
	@Value("${jwt.refresh-token-expiration-minutes}")
	private int REFRESH_TOKEN_EXPIRE_TIME;
	private final Key key;
	public TokenProvider(@Value("${jwt.secret-key}") String secretKey) {
		byte[] keyBytes = Decoders.BASE64URL.decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}

	public Date getTokenExpiration(int expirationMinutes) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, expirationMinutes);
		Date expiration = calendar.getTime();

		return expiration;
	}

	public TokenDto generateTokenDto(AuthMember authMember) {
		// 권한들 가져오기
		String authorities = authMember.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.joining(","));

		Date accessTokenExpiresIn = getTokenExpiration(ACCESS_TOKEN_EXPIRE_TIME);
		Date refreshTokenExpiresIn = getTokenExpiration(REFRESH_TOKEN_EXPIRE_TIME);

		Map<String, Object> claims = new HashMap<>();
		claims.put("id", authMember.getMemberId());
		claims.put("roles", authMember.getAuthorities());

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

	public Authentication getAuthentication(String accessToken) {
		// 토큰 복호화
		Claims claims = parseClaims(accessToken);

		if (claims.get("roles") == null) {
			throw new RuntimeException("권한 정보가 없는 토큰입니다.");
		}

		// 클레임에서 권한 정보 가져오기
		List<String> authorities = Arrays.stream(claims.get("roles").toString().split(","))
			.collect(Collectors.toList());

		AuthMember auth = AuthMember.of(claims.get("id", Long.class), authorities);
		return new UsernamePasswordAuthenticationToken(auth, auth.getPassword(), auth.getAuthorities());
	}

	// 토큰 검증
	public boolean validateToken(String token) {

		try {
			parseClaims(token);
			return true;
		} catch (SignatureException e) {
			log.info("Invalid JWT signature");
			log.trace("Invalid JWT signature trace: {}", e);
			throw new TokenSignatureInvalid();
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
