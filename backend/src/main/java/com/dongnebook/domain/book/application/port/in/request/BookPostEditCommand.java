package com.dongnebook.domain.book.application.port.in.request;

import com.dongnebook.domain.book.adapter.in.request.BookPostEditRequest;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookPostEditCommand {
	private String description;
	private String imageUrl;

	public BookPostEditCommand(BookPostEditRequest request) {
		this.description = request.getDescription();
		this.imageUrl = request.getImageUrl();
	}

	public static BookPostEditCommand of(BookPostEditRequest request){
		return new BookPostEditCommand(request);
	}
}
