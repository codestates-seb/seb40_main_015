package com.dongnebook.domain.book.adapter.in;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.SliceImpl;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dongnebook.domain.book.adapter.in.request.BookPostEditRequest;
import com.dongnebook.domain.book.adapter.in.request.BookPostRegisterRequest;
import com.dongnebook.domain.book.adapter.in.request.BookSearchCondition;
import com.dongnebook.domain.book.application.port.in.BookPostCommandUseCase;
import com.dongnebook.domain.book.application.port.in.BookPostQueryUseCase;
import com.dongnebook.domain.book.application.port.in.request.BookPostEditCommand;
import com.dongnebook.domain.book.application.port.in.request.BookPostRegisterCommand;
import com.dongnebook.domain.book.application.port.in.request.BookSearchCommand;
import com.dongnebook.domain.book.application.port.in.response.ApiBookInfoResponse;
import com.dongnebook.domain.book.application.port.in.response.BookCountPerSectorResponse;
import com.dongnebook.domain.book.application.port.in.response.BookDetailResponse;
import com.dongnebook.domain.book.application.port.in.response.BookSimpleResponse;
import com.dongnebook.global.dto.request.PageRequestImpl;
import com.dongnebook.global.security.auth.annotation.Login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

	private final BookPostCommandUseCase bookPostCommandUseCase;
	private final BookPostQueryUseCase bookPostQueryUseCase;
	private final BookInfoParser bookInfoParser;
	private final BookInfoFetcher<String> bookInfoFetcher;

	@PostMapping
	public ResponseEntity<Long> create(@Valid @RequestBody BookPostRegisterRequest bookPostRegisterRequest,
		@Login Long memberId) {
		BookPostRegisterCommand command = BookPostRegisterCommand.of(bookPostRegisterRequest);
		Long bookId = bookPostCommandUseCase.register(command, memberId);
		URI createdUri = ServletUriComponentsBuilder.fromCurrentRequestUri()
			.path("/{id}")
			.buildAndExpand(bookId)
			.toUri();
		return ResponseEntity.created(createdUri).build();
	}

	@PatchMapping("/{id}")
	public void edit(@Login Long memberId, @PathVariable Long id, @Valid @RequestBody BookPostEditRequest bookPostEditRequest) {
		BookPostEditCommand command = BookPostEditCommand.of(bookPostEditRequest);
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
		return ResponseEntity.ok(bookPostQueryUseCase.getList(command, PageRequestImpl.of(index)));
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
		return ResponseEntity.ok(bookPostQueryUseCase.getList(command, PageRequestImpl.of(index)));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Long> delete(@PathVariable Long id, @Login Long memberId) {
		return ResponseEntity.ok(bookPostCommandUseCase.delete(id, memberId));
	}

	@GetMapping("/bookInfo")
	public List<ApiBookInfoResponse> getBookInfo(@RequestParam String bookTitle) {
		ResponseEntity<String> bookInfo = bookInfoFetcher.getBookInfo(bookTitle);
		return bookInfoParser.toResponse(bookInfo);
	}

}