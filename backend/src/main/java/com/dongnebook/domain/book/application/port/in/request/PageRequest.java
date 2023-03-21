package com.dongnebook.domain.book.application.port.in.request;

public interface PageRequest {
	Long getIndex();
	Long getSize();
	PageRequest of(Long index);
	org.springframework.data.domain.PageRequest of();
}
