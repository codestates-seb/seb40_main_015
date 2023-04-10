package com.dongnebook.domain.book.adapter.in.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class BookPostRegisterRequest {
	@NotEmpty
	private String title;
	@NotEmpty
	private String author;
	@NotEmpty
	private String publisher;
	@NotEmpty
	private String description;
	private String imageUrl;
	@Min(0)
	private Integer rentalFee;

	@Builder
	public BookPostRegisterRequest(String title, String author, String publisher, String description, String imageUrl,
		Integer rentalFee) {
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.description = description;
		this.imageUrl = imageUrl;
		this.rentalFee = rentalFee;
	}
}
