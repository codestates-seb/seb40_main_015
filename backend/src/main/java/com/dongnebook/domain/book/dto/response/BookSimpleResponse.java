package com.dongnebook.domain.book.dto.response;

import java.io.Serializable;

import com.dongnebook.domain.book.domain.BookState;
import com.dongnebook.domain.book.domain.Money;
import com.dongnebook.domain.model.Location;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class BookSimpleResponse implements Serializable {
	private Long bookId;
	private String title;
	private String status;
	private String bookImage;
	private Integer rentalFee;
	private Location location;
	private String merchantName;

	@Builder
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

	@Builder
	@QueryProjection
	public BookSimpleResponse(Long bookId, String title, BookState status, String bookImage) {
		this.bookId = bookId;
		this.title = title;
		this.status = status.getMessage();
		this.bookImage = bookImage;
	}

	@Builder
	@QueryProjection
	public BookSimpleResponse(Long bookId, String title, Money rentalFee, String bookImage, String merchantName) {
		this.bookId = bookId;
		this.title = title;
		this.rentalFee = rentalFee.getValue();
		this.bookImage = bookImage;
		this.merchantName = merchantName;
	}
}
