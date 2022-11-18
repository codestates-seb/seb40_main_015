package com.dongnebook.global.dto.request;

import org.springframework.data.domain.Sort;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public final class PageRequest {

	private Long index;
	private Long size = 6L;
	private Sort.Direction sort= Sort.Direction.DESC;

	public PageRequest(Long index) {
		this.index = index;
	}


	// getter
	public org.springframework.data.domain.PageRequest of() {
		return org.springframework.data.domain.PageRequest.of(0,size.intValue());
	}


}
