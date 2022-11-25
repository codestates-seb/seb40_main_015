package com.dongnebook.domain.book.ui;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import com.dongnebook.domain.book.application.BookService;
import com.dongnebook.domain.book.dto.KaKaoBookInfoResponse;
import com.dongnebook.domain.book.dto.request.BookRegisterRequest;
import com.dongnebook.domain.book.dto.request.BookSearchCondition;
import com.dongnebook.domain.book.dto.response.BookDetailResponse;
import com.dongnebook.domain.book.dto.response.BookSectorCountResponse;
import com.dongnebook.domain.book.dto.response.BookSimpleResponse;
import com.dongnebook.global.Login;
import com.dongnebook.global.config.security.auth.userdetails.AuthMember;
import com.dongnebook.global.dto.request.PageRequest;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {
	@Value("${KAKAO_KEY}")
	private String KaKaoKey;
	private final BookService bookService;

	@PostMapping
	public ResponseEntity<Long> create(@Valid @RequestBody BookRegisterRequest bookRegisterRequest,
		@Login AuthMember authMember) {

		Long bookId = bookService.create(bookRegisterRequest, authMember.getMemberId());
		URI createdUri = ServletUriComponentsBuilder.fromCurrentRequestUri()
			.path("/{id}")
			.buildAndExpand(bookId)
			.toUri();
		return ResponseEntity.created(createdUri).build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<BookDetailResponse> getDetail(@PathVariable Long id) {

		BookDetailResponse detail = bookService.getDetail(id);

		return ResponseEntity.ok(detail);
	}

	@GetMapping
	public ResponseEntity<SliceImpl<BookSimpleResponse>> getLists(
		@ModelAttribute BookSearchCondition bookSearchCondition, PageRequest pageRequest) {
		log.info("location = {}", bookSearchCondition.getLatitude());
		log.info("bookTitle = {}", bookSearchCondition.getBookTitle());
		log.info("location = {}", bookSearchCondition.getLongitude());
		return ResponseEntity.ok(bookService.getList(bookSearchCondition, pageRequest));
	}

	@GetMapping("/count")
	public ResponseEntity<ArrayList<BookSectorCountResponse>> getSectorBookCounts(
		@ModelAttribute BookSearchCondition bookSearchCondition) {
		ArrayList<BookSectorCountResponse> sectorBookCounts = bookService.getSectorBookCounts(bookSearchCondition);
		return ResponseEntity.ok(sectorBookCounts);
	}

	@GetMapping("/sector")
	public ResponseEntity<SliceImpl<BookSimpleResponse>> getSectors(
		@ModelAttribute BookSearchCondition bookSearchCondition, PageRequest pageRequest) {
		log.info("location = {}", bookSearchCondition.getLatitude());
		log.info("bookTitle = {}", bookSearchCondition.getBookTitle());
		log.info("location = {}", bookSearchCondition.getLongitude());
		return ResponseEntity.ok(bookService.getList(bookSearchCondition, pageRequest));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Long> delete(@PathVariable Long id, @Login AuthMember authMember) {
		return ResponseEntity.ok(bookService.delete(id, authMember.getMemberId()));
	}

	@GetMapping("/bookInfo")
	public ArrayList<KaKaoBookInfoResponse> getBookInfo(@RequestParam String bookTitle) throws IOException {
		String testurl = "https://dapi.kakao.com/v3/search/book";
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Authorization", "KakaoAK " + KaKaoKey);

		log.info("bookTitle = {}", bookTitle);
		URI uri = UriComponentsBuilder.fromHttpUrl(testurl)
			.queryParam("query", bookTitle)
			.queryParam("target", "title")
			.build().toUri();

		RestTemplate restTemplate = new RestTemplate();
		log.info("uri = {}", uri);
		HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

		log.info("entity = {}", entity);
		ResponseEntity<String> exchange = restTemplate.exchange(uri.toString(), HttpMethod.GET, entity, String.class);
		JsonElement jsonElement = JsonParser.parseString(Objects.requireNonNull(exchange.getBody()));
		JsonObject asJsonObject = jsonElement.getAsJsonObject();
		JsonArray documents = asJsonObject.get("documents").getAsJsonArray();

		ArrayList<KaKaoBookInfoResponse> list = new ArrayList<>();
		for (JsonElement document : documents) {
			JsonObject jsonObject = document.getAsJsonObject();
			ArrayList<String> authors = new ArrayList<>();
			for (JsonElement author : jsonObject.get("authors").getAsJsonArray()) {
				authors.add(author.getAsString());
			}
			list.add(KaKaoBookInfoResponse.builder()
				.authors(authors)
				.title(jsonObject.get("title").getAsString())
				.publisher(jsonObject.get("publisher").getAsString())
				.build());
		}

		return list;
	}

}
