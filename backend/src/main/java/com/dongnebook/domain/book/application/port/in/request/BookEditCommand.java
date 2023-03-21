package com.dongnebook.domain.book.application.port.in.request;

import com.dongnebook.domain.book.adapter.in.request.BookEditRequest;

import lombok.Getter;

@Getter
public class BookEditCommand {
	private final String description;
	private final String imageUrl;

	public BookEditCommand(BookEditRequest request) {
		this.description = request.getDescription();
		this.imageUrl = request.getImageUrl();
	}

	public static BookEditCommand of(BookEditRequest request){
		return new BookEditCommand(request);
	}
}
