package com.dongnebook.domain.book.application.port.in.request;

import com.dongnebook.domain.book.adapter.in.request.BookPostEditRequest;

import lombok.Getter;

@Getter
public class BookPostEditCommand {
	private final String description;
	private final String imageUrl;

	public BookPostEditCommand(BookPostEditRequest request) {
		this.description = request.getDescription();
		this.imageUrl = request.getImageUrl();
	}

	public static BookPostEditCommand of(BookPostEditRequest request){
		return new BookPostEditCommand(request);
	}
}
