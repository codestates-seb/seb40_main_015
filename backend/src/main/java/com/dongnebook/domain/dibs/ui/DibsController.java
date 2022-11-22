package com.dongnebook.domain.dibs.ui;

import org.springframework.data.domain.SliceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RestController;

import com.dongnebook.domain.book.dto.response.BookSimpleResponse;
import com.dongnebook.domain.dibs.service.DibsService;
import com.dongnebook.global.Login;
import com.dongnebook.global.config.security.auth.userdetails.AuthMember;

import com.dongnebook.global.dto.request.PageRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class DibsController {

	private final DibsService dibsService;

	@PostMapping("/dibs/{id}")
	public void findAll(@PathVariable Long id,@Login AuthMember authMember){
		//멤버 가져오는 로직

		dibsService.doDibs(id, authMember.getMemberId());
	}

	@GetMapping("/dibs")
	public ResponseEntity<SliceImpl<BookSimpleResponse>> findAllMyDibs(PageRequest pageRequest,@AuthenticationPrincipal AuthMember member){
		return ResponseEntity.ok(dibsService.findAll(pageRequest,member.getMemberId()));
	}




}
