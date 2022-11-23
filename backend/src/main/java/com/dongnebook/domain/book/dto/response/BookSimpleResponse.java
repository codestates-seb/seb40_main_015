package com.dongnebook.domain.book.dto.response;

import com.dongnebook.domain.book.domain.BookState;
import com.dongnebook.domain.book.domain.Money;
import com.dongnebook.domain.model.Location;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryProjection;


import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookSimpleResponse {
	private Long bookId;
	private String title;
	private String status;
	private String bookImage;
	private Integer rentalFee;
	private Location location;
	private String merchantName;

	@QueryProjection
	public BookSimpleResponse(Long bookId, String title, BookState status, String bookImage, Money rentalFee,
		Location location, String merchantName) {
		this.bookId = bookId;
		this.title = title;
		this.status = status.getMessage();
		this.bookImage = bookImage;
		this.rentalFee = rentalFee.getValue();
		this.location = location;
		this.merchantName = merchantName;
	}

	@QueryProjection
	public BookSimpleResponse(Long bookId, String title, BookState status, String bookImage) {
		this.bookId = bookId;
		this.title = title;
		this.status = status.getMessage();
		this.bookImage = bookImage;
	}
}
