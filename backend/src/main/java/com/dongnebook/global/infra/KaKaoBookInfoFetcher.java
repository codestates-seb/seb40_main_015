package com.dongnebook.global.infra;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.dongnebook.domain.book.adapter.in.BookInfoFetcher;

import lombok.Getter;

@Getter
@Component
public class KaKaoBookInfoFetcher implements BookInfoFetcher<String> {
	private static final String KAKAO_BOOKSEARCH_API = "https://dapi.kakao.com/v3/search/book";
	private static final String KAKAOAK = "KakaoAK ";

	@Value("${KAKAO_KEY}")
	private String kakaoKey;

	public KaKaoBookInfoFetcher(@Value("${KAKAO_KEY}") String kakaoKey) {
		this.kakaoKey = kakaoKey;
	}

	@Override
	public ResponseEntity<String> getBookInfo(String bookTitle) {
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> entity = toHttpEntity();
		URI uri = toURI(bookTitle);
		return restTemplate.exchange(uri.toString(), HttpMethod.GET, entity, String.class);
	}

	private HttpEntity<String> toHttpEntity() {
		HttpHeaders httpHeaders = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
		httpHeaders.set(HttpHeaders.AUTHORIZATION, KAKAOAK + kakaoKey);
		return entity;
	}

	private URI toURI(String bookTitle) {
		return UriComponentsBuilder.fromHttpUrl(KAKAO_BOOKSEARCH_API)
			.queryParam("query", bookTitle)
			.queryParam("target", "title")
			.queryParam("size", 50)
			.build()
			.toUri();
	}
}
