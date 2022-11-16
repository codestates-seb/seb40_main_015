package com.dongnebook.global.dto.request;

import org.springframework.data.domain.Sort;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public final class PageRequest {

	private static final int MAX_SIZE = 2000;



	private int index;
	private int size = 6;
	private Sort.Direction sort= Sort.Direction.DESC;

	public PageRequest(Integer index) {
		this.index = index==null ? -1 : index;
	}


	// getter
	public org.springframework.data.domain.PageRequest of() {
		return org.springframework.data.domain.PageRequest.of(0,size);
	}


}
