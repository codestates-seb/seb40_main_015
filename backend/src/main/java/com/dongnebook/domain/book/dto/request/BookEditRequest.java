package com.dongnebook.domain.book.dto.request;

import javax.validation.constraints.NotEmpty;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookEditRequest {
	@NotEmpty
	private String description;
	private String imageUrl;

	@Builder
	public BookEditRequest(String description, String imageUrl) {
		this.description = description;
		this.imageUrl = imageUrl;
	}
}
