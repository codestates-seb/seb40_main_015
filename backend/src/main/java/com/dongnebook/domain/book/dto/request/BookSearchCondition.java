package com.dongnebook.domain.book.dto.request;

import javax.validation.constraints.Max;

import lombok.Getter;

/**
 * 	만약 length 20 width 20 이 들어왔으면 1km, 1km
 *  500/1.1, 500/0.9
 * 	range 로 나눠서 뿌려줌
 */
@Getter
public class BookSearchCondition {
	private final String bookTitle;
	private final Double longitude;
	private final Double latitude;
	private final Integer width;
	private final Integer height;
	private final Integer sector;
	@Max(10)
	private final Integer level;


	public BookSearchCondition(String bookTitle, Double longitude, Double latitude, Integer width, Integer height,
		Integer sector, Integer level) {
		this.bookTitle = bookTitle == null ? "" : bookTitle;
		this.longitude = longitude;
		this.latitude = latitude;
		this.width = width == null ? 1000 : width * 50;
		this.height = height == null ? 1000 : height * 50;
		this.sector = sector;
		this.level = level;
	}
}
