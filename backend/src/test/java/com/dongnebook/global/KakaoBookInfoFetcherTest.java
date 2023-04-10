package com.dongnebook.global;

import static org.assertj.core.api.Assertions.*;

import java.net.URI;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.RequestMatcher;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestTemplate;

import com.dongnebook.global.infra.KaKaoBookInfoFetcher;


class KakaoBookInfoFetcherTest {

		private static final String KAKAO_KEY = System.getenv("KAKAO_KEY");
		private static final String KAKAO_BOOKSEARCH_API = "https://dapi.kakao.com/v3/search/book";
		private static final String KAKAOAK = "KakaoAK ";


		void getBookInfo_shouldReturnValidResponse() {
			RestTemplate restTemplate = new RestTemplate();
			KaKaoBookInfoFetcher kaKaoBookInfoFetcher = new KaKaoBookInfoFetcher(KAKAO_KEY);
			MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);

			HttpHeaders expectedHeaders = new HttpHeaders();
			expectedHeaders.set(HttpHeaders.AUTHORIZATION, KAKAOAK + KAKAO_KEY);

			String bookTitle = "자바의정석";
			URI expectedUri = URI.create(KAKAO_BOOKSEARCH_API + "?query=" + bookTitle + "&target=title&size=50");

			String expectedResponseBody = "testResponseBody";

			mockServer.expect(requestTo(expectedUri))
				.andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
				.andExpect(MockRestRequestMatchers.header(HttpHeaders.AUTHORIZATION, KAKAOAK + KAKAO_KEY))
				.andRespond(MockRestResponseCreators.withSuccess(expectedResponseBody, MediaType.APPLICATION_JSON));

			// when
			ResponseEntity<String> actualResponse = kaKaoBookInfoFetcher.getBookInfo(bookTitle);

			// then
			assertThat(actualResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		}

		private RequestMatcher requestTo(URI uri) {
			return MockRestRequestMatchers.requestTo(uri);
		}
	}

