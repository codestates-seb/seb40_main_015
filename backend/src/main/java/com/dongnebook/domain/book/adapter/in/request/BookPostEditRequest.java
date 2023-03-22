package com.dongnebook.domain.book.adapter.in.request;

import javax.validation.constraints.NotEmpty;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookPostEditRequest {
	@NotEmpty
	private String description;
	private String imageUrl;

	@Builder
	public BookPostEditRequest(String description, String imageUrl) {
		this.description = description;
		this.imageUrl = imageUrl;
	}
}
