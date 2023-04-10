package com.dongnebook.domain.book.adapter.in;

import org.springframework.http.ResponseEntity;

public interface BookInfoFetcher<T> {
	ResponseEntity<T> getBookInfo(String bookTitle);
}
