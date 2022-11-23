package com.dongnebook.domain.book.dto.response;

import java.time.LocalDateTime;

import com.dongnebook.domain.book.domain.BookState;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;


import lombok.Getter;

@Getter
public class BookResponse {

	private Long bookId;
	private String title;
	private String publisher;
	private Integer rentalFee;
	private String content;
	private String state;
	private String bookImgUrl;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private LocalDateTime rentalStart;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private LocalDateTime rentalEnd;

	@QueryProjection
	public BookResponse(Long bookId, String title, String publisher, Integer rentalFee, String content, BookState state,
		String bookImgUrl) {
		this.bookId = bookId;
		this.title = title;
		this.publisher = publisher;
		this.rentalFee = rentalFee;
		this.content = content;
		this.state = state.getMessage();
		this.bookImgUrl = bookImgUrl;
	}
}
