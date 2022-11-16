package com.dongnebook.domain.book.ui;

import java.net.URI;
import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dongnebook.domain.book.application.BookService;
import com.dongnebook.domain.book.dto.request.BookRegisterRequest;
import com.dongnebook.domain.book.dto.request.SectorBookCountRequest;
import com.dongnebook.domain.book.dto.response.BookDetailResponse;
import com.dongnebook.domain.book.dto.response.BookSectorCountResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

	private final BookService bookService;

	@PostMapping
	public ResponseEntity<Long> create(@Valid @RequestBody BookRegisterRequest bookRegisterRequest){

		Long bookId = bookService.create(bookRegisterRequest, 1L);
		URI createdUri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(bookId).toUri();

		return ResponseEntity.created(createdUri).build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<BookDetailResponse> getDetail(@PathVariable Long id){

		BookDetailResponse detail = bookService.getDetail(id);

		return ResponseEntity.ok(detail);
	}

	@GetMapping
	public ResponseEntity<ArrayList<BookSectorCountResponse>> getSectorBookCounts(@ModelAttribute SectorBookCountRequest sectorBookCountRequest){
		ArrayList<BookSectorCountResponse> sectorBookCounts = bookService.getSectorBookCounts(sectorBookCountRequest);

		return ResponseEntity.ok(sectorBookCounts);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Long> delete(@PathVariable Long id) {
		return ResponseEntity.ok(bookService.delete(id, 1L));
	}


}
