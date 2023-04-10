package com.dongnebook.domain.book.application.port.in.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Builder;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiBookInfoResponse {
	private final List<String> authors;
	private final String publisher;
	private final String title;

	@Builder
	public ApiBookInfoResponse(List<String> authors, String publisher, String title) {
		this.authors = authors;
		this.publisher = publisher;
		this.title = title;
	}
}
