package com.dongnebook.domain.dibs.ui;

import org.springframework.data.domain.SliceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dongnebook.domain.book.application.port.in.response.BookSimpleResponse;
import com.dongnebook.domain.dibs.service.DibsService;
import com.dongnebook.global.dto.request.PageRequestImpl;
import com.dongnebook.global.security.auth.annotation.Login;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class DibsController {
	private final DibsService dibsService;

	@PostMapping("/dibs/{id}")
	public void findAll(@PathVariable Long id,@Login Long memberId){
		dibsService.doDibs(id, memberId);
	}

	@GetMapping("/dibs")
	public ResponseEntity<SliceImpl<BookSimpleResponse>> findAllMyDibs(PageRequestImpl pageRequestImpl,@Login Long memberId){
		return ResponseEntity.ok(dibsService.findAll(pageRequestImpl,memberId));
	}




}
