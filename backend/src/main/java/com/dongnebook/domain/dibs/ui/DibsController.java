package com.dongnebook.domain.dibs.ui;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dongnebook.domain.dibs.service.DibsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class DibsController {

	private final DibsService dibsService;
	@PostMapping("/dibs/{id}")
	public void doDibs(@PathVariable Long id,@RequestBody Long memberId){
		//멤버 가져오는 로직
		dibsService.doDibs(id,memberId);
	}

	@DeleteMapping("/dibs/{id}")
	public void cancelDibs(@PathVariable Long id,@RequestBody Long memberId){
		//멤버 가져오는 로직
		dibsService.cancelDibs(id,memberId);
	}
}
