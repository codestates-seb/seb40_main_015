package com.dongnebook.domain.book.adapter.in.request;

import java.util.Optional;

import com.dongnebook.domain.book.application.port.in.request.MapSearchable;

import lombok.Getter;

/**
 * 	만약 length 20 width 20 이 들어왔으면 1km, 1km
 *  500/1.1, 500/0.9
 * 	range 로 나눠서 뿌려줌
 */
@Getter
public class BookSearchCondition implements MapSearchable {
	private final String bookTitle;
	private final SearchRequest searchRequest;

	public BookSearchCondition(String bookTitle, Double longitude, Double latitude, Integer width, Integer height,
		Integer sector, Integer level) {
		this.bookTitle =Optional.ofNullable(bookTitle).orElse("");
		this.searchRequest = new SearchRequest(longitude, latitude, width, height, sector, level);
	}

	@Override
	public Integer getLevel(){
		return searchRequest.getLevel();
	}

	@Override
	public Double getLongitude() {
		return searchRequest.getLongitude();
	}

	@Override
	public Double getLatitude() {
		return searchRequest.getLatitude();
	}

	@Override
	public Integer getWidth() {
		return searchRequest.getWidth();
	}

	@Override
	public Integer getHeight() {
		return searchRequest.getHeight();
	}

	@Override
	public Integer getSector() {
		return searchRequest.getSector();
	}
}
