package com.dongnebook.domain.book.dto.response;

import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.member.dto.response.BookDetailMemberResponse;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class BookDetailResponse {
	private BookResponse book;
	private BookDetailMemberResponse merchant;

	@QueryProjection
	public BookDetailResponse(BookResponse book, BookDetailMemberResponse merchant) {
		this.book = book;
		this.merchant = merchant;
	}
}
