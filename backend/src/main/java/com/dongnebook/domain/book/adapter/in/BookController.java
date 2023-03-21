package com.dongnebook.domain.book.adapter.in;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import com.dongnebook.domain.book.adapter.in.request.BookEditRequest;
import com.dongnebook.domain.book.adapter.in.request.BookRegisterRequest;
import com.dongnebook.domain.book.adapter.in.request.BookSearchCondition;
import com.dongnebook.domain.book.application.port.in.request.BookSearchCommand;
import com.dongnebook.domain.book.application.port.in.request.PageRequest;
import com.dongnebook.domain.book.application.port.in.response.BookCountPerSectorResponse;
import com.dongnebook.domain.book.application.port.in.response.BookDetailResponse;
import com.dongnebook.domain.book.application.port.in.response.BookSimpleResponse;
import com.dongnebook.domain.book.application.port.in.response.KaKaoBookInfoResponse;
import com.dongnebook.domain.book.application.port.in.request.BookEditCommand;
import com.dongnebook.domain.book.application.port.in.BookPostCommandUseCase;
import com.dongnebook.domain.book.application.port.in.BookPostQueryUseCase;
import com.dongnebook.domain.book.application.port.in.request.BookPostRegisterCommand;

import com.dongnebook.global.security.auth.annotation.Login;
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
	private String kakaoKey;
	private final BookPostCommandUseCase bookPostCommandUseCase;
	private final BookPostQueryUseCase bookPostQueryUseCase;
	private final PageRequest pageRequest;

	@PostMapping
	public ResponseEntity<Long> create(@Valid @RequestBody BookRegisterRequest bookRegisterRequest,
		@Login Long memberId) {
		BookPostRegisterCommand command = BookPostRegisterCommand.of(bookRegisterRequest);
		Long bookId = bookPostCommandUseCase.register(command, memberId);
		URI createdUri = ServletUriComponentsBuilder.fromCurrentRequestUri()
			.path("/{id}")
			.buildAndExpand(bookId)
			.toUri();
		return ResponseEntity.created(createdUri).build();
	}

	@PatchMapping("/{id}")
	public void edit(@Login Long memberId, @PathVariable Long id, @Valid @RequestBody BookEditRequest bookEditRequest) {
		BookEditCommand command = BookEditCommand.of(bookEditRequest);
		bookPostCommandUseCase.edit(command,memberId, id);
	}

	@GetMapping("/{id}")
	public ResponseEntity<BookDetailResponse> getDetail(@Login Long memberId, @PathVariable Long id) {
		BookDetailResponse detail = bookPostQueryUseCase.getDetail(id, memberId);
		return ResponseEntity.ok(detail);
	}

	@GetMapping
	public ResponseEntity<SliceImpl<BookSimpleResponse>> getLists(
		@ModelAttribute BookSearchCondition bookSearchCondition,Long index) {
		BookSearchCommand command = BookSearchCommand.of(bookSearchCondition);
		return ResponseEntity.ok(bookPostQueryUseCase.getList(command, pageRequest.of(index)));
	}

	@GetMapping("/count")
	public ResponseEntity<List<BookCountPerSectorResponse>> getSectorBookCounts(
		@ModelAttribute BookSearchCondition bookSearchCondition) {
		BookSearchCommand command = BookSearchCommand.of(bookSearchCondition);
		List<BookCountPerSectorResponse> sectorBookCounts = bookPostQueryUseCase.getBookCountPerSector(command);
		return ResponseEntity.ok(sectorBookCounts);
	}

	@GetMapping("/sector")
	public ResponseEntity<SliceImpl<BookSimpleResponse>> getSectors(
		@ModelAttribute BookSearchCondition bookSearchCondition, Long index) {
		BookSearchCommand command = BookSearchCommand.of(bookSearchCondition);
		return ResponseEntity.ok(bookPostQueryUseCase.getList(command, pageRequest.of(index)));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Long> delete(@PathVariable Long id, @Login Long memberId) {
		return ResponseEntity.ok(bookPostCommandUseCase.delete(id, memberId));
	}

	@GetMapping("/bookInfo")
	public List<KaKaoBookInfoResponse> getBookInfo(@RequestParam String bookTitle) {
		String kakaoApi = "https://dapi.kakao.com/v3/search/book";
		HttpHeaders httpHeaders = new HttpHeaders();
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
		List<KaKaoBookInfoResponse> list = new ArrayList<>();
		httpHeaders.set("Authorization", "KakaoAK " + kakaoKey);

		URI uri = UriComponentsBuilder.fromHttpUrl(kakaoApi)
			.queryParam("query", bookTitle)
			.queryParam("target", "title")
			.queryParam("size", 50)
			.build()
			.toUri();

		JsonArray documents = getJsonElements(restTemplate, entity, uri);

		for (JsonElement document : documents) {
			List<String> authors = new ArrayList<>();
			JsonObject jsonObject = document.getAsJsonObject();

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

	private JsonArray getJsonElements(RestTemplate restTemplate, HttpEntity<String> entity, URI uri) {
		ResponseEntity<String> exchange = restTemplate.exchange(uri.toString(), HttpMethod.GET, entity, String.class);
		JsonElement jsonElement = JsonParser.parseString(Objects.requireNonNull(exchange.getBody()));
		JsonObject asJsonObject = jsonElement.getAsJsonObject();
		return asJsonObject.get("documents").getAsJsonArray();
	}
}