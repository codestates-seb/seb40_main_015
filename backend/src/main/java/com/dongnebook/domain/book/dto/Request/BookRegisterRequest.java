package com.dongnebook.domain.book.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.dongnebook.domain.model.Location;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BookRegisterRequest {

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
	@NotNull
	private Location location;

	@Builder
	public BookRegisterRequest(String title, String author, String publisher, String description, String imageUrl,
		Integer rentalFee, Location location) {
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.description = description;
		this.imageUrl = imageUrl;
		this.rentalFee = rentalFee;
		this.location = location;
	}
}
