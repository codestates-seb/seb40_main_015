package com.dongnebook.domain.book.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Builder;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KaKaoBookInfoResponse {
	private List<String> authors;
	private String publisher;
	private String title;

	@Builder
	public KaKaoBookInfoResponse(List<String> authors, String publisher, String title) {
		this.authors = authors;
		this.publisher = publisher;
		this.title = title;
	}
}
