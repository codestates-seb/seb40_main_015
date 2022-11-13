package com.dongnebook.domain.book.ui;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

	@PostMapping
	public ResponseEntity<Long> create(){

		URI createdUri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(1L).toUri();
		return ResponseEntity.created(createdUri).build();
	}
}
