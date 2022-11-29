package com.dongnebook.domain.book.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.dongnebook.domain.book.domain.BookState;
import com.dongnebook.domain.dibs.domain.Dibs;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;


import lombok.Getter;

@Getter
public class BookResponse {

	private Long bookId;
	private String title;
	private String author;
	private String publisher;
	private Integer rentalFee;
	private String content;
	private String state;
	private String bookImgUrl;
	private Boolean isDibs;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private LocalDateTime rentalStart;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private LocalDateTime rentalEnd;

	@QueryProjection
	public BookResponse(Long bookId, String title, String author, String publisher, Integer rentalFee, String content, BookState state,
		String bookImgUrl, Long dibsId) {
		this.bookId = bookId;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.rentalFee = rentalFee;
		this.content = content;
		this.state = state.getMessage();
		this.bookImgUrl = bookImgUrl;
		this.isDibs = dibsId == 0L ? Boolean.FALSE : Boolean.TRUE;
	}
}
