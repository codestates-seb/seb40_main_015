package com.dongnebook.domain.book.application.port.in.response;

import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.book.domain.BookState;
import com.dongnebook.domain.model.Location;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class BookInfoResponse {
	private final Long bookId;
	private final String bookUrl;
	private final String title;
	private final String author;
	private final String publisher;
	private final Integer rentalFee;
	private final String content;
	private final Location location;
	private final BookState bookState;
	private final String merchantName;
	private final Long merchantId;

	@QueryProjection
	public BookInfoResponse(Long bookId, String bookUrl, String title, String author, String publisher,
		Integer rentalFee, String content, Location location, BookState bookState, String merchantName,
		Long merchantId) {
		this.bookId = bookId;
		this.bookUrl = bookUrl;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.rentalFee = rentalFee;
		this.content = content;
		this.location = location;
		this.bookState = bookState;
		this.merchantName = merchantName;
		this.merchantId = merchantId;
	}

	public static BookInfoResponse of(Book book) {
		return new BookInfoResponse(book.getId(), book.getImgUrl(), book.getBookProduct().getTitle(),
			book.getBookProduct().getAuthor(), book.getBookProduct().getPublisher(), book.getRentalFee().getValue(),
			book.getDescription(), book.getLocation(),
			book.getBookState(), book.getMember().getNickname(), book.getMember().getId());
	}
}

