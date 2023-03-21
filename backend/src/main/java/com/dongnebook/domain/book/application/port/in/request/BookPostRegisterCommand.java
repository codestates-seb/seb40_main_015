package com.dongnebook.domain.book.application.port.in.request;

import com.dongnebook.domain.book.adapter.in.request.BookRegisterRequest;

import lombok.Getter;

@Getter
public class BookPostRegisterCommand {

	private final String title;
	private final String author;
	private final String publisher;
	private final String description;
	private final String imageUrl;
	private final Integer rentalFee;

	private BookPostRegisterCommand(BookRegisterRequest request) {
		this.title = request.getTitle();
		this.author = request.getAuthor();
		this.publisher = request.getPublisher();
		this.description = request.getDescription();
		this.imageUrl = request.getImageUrl();
		this.rentalFee = request.getRentalFee();
	}

	public static BookPostRegisterCommand of(BookRegisterRequest request){
		return new BookPostRegisterCommand(request);
	}
}
