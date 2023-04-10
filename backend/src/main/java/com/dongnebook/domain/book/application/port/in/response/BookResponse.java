package com.dongnebook.domain.book.application.port.in.response;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.dongnebook.domain.book.domain.BookState;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookResponse implements Serializable {
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

	@Builder
	@QueryProjection
	public BookResponse(Long bookId, String title, String author, String publisher, Integer rentalFee, String content,
		BookState state, String bookImgUrl, Long dibsId, LocalDateTime rentalStart, LocalDateTime rentalEnd) {
		this.bookId = bookId;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.rentalFee = rentalFee;
		this.content = content;
		this.state = state.getMessage();
		this.bookImgUrl = bookImgUrl;
		this.isDibs = dibsId == null ? Boolean.FALSE : Boolean.TRUE;
		this.rentalStart = rentalStart;
		this.rentalEnd = rentalEnd;
	}
}
