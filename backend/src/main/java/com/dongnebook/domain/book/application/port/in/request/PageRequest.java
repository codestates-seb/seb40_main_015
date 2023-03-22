package com.dongnebook.domain.book.application.port.in.request;

public interface PageRequest {
	Long getIndex();
	Long getSize();
	org.springframework.data.domain.PageRequest of();
}
