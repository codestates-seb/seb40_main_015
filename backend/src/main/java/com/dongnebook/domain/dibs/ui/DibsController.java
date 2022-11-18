package com.dongnebook.domain.dibs.ui;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dongnebook.domain.dibs.service.DibsService;
import com.dongnebook.global.config.security.auth.userdetails.AuthMember;

import com.dongnebook.global.dto.request.PageRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class DibsController {

	private final DibsService dibsService;

	@PostMapping("/dibs/{id}")
	public void doDibs(@PathVariable Long id,@AuthenticationPrincipal AuthMember authMember){
		//멤버 가져오는 로직

		dibsService.doDibs(id, authMember.getMemberId());
	}

	@GetMapping("/dibs")
	public void doDibs(@RequestBody PageRequest pageRequest,@AuthenticationPrincipal AuthMember member){
		dibsService.findAll(pageRequest,member.getMemberId());
	}




}
