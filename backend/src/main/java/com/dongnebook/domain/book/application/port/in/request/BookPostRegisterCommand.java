package com.dongnebook.domain.book.application.port.in.request;

import com.dongnebook.domain.book.adapter.in.request.BookPostRegisterRequest;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookPostRegisterCommand {

	private String title;
	private String author;
	private String publisher;
	private String description;
	private String imageUrl;
	private Integer rentalFee;

	private BookPostRegisterCommand(BookPostRegisterRequest request) {
		this.title = request.getTitle();
		this.author = request.getAuthor();
		this.publisher = request.getPublisher();
		this.description = request.getDescription();
		this.imageUrl = request.getImageUrl();
		this.rentalFee = request.getRentalFee();
	}

	public static BookPostRegisterCommand of(BookPostRegisterRequest request){
		return new BookPostRegisterCommand(request);
	}
}
