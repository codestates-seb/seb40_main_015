package com.dongnebook.domain.book.application.port.in.response;

import java.io.Serializable;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookDetailResponse implements Serializable {
	private BookResponse book;
	private BookDetailMemberResponse merchant;

	@Builder
	@QueryProjection
	public BookDetailResponse(BookResponse book, BookDetailMemberResponse merchant) {
		this.book = book;
		this.merchant = merchant;
	}
}
