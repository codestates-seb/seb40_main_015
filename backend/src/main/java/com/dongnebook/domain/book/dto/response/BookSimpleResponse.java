package com.dongnebook.domain.book.dto.response;

import com.dongnebook.domain.book.domain.BookState;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BookSimpleResponse {
	private Long bookId;
	private String title;
	private String status;
	private String bookImage;
	private String merchantName;

	@QueryProjection
	public BookSimpleResponse(Long bookId, String title, BookState status, String bookImage, String merchantName) {
		this.bookId = bookId;
		this.title = title;
		this.status = status.getMessage();
		this.bookImage = bookImage;
		this.merchantName = merchantName;
	}
}
