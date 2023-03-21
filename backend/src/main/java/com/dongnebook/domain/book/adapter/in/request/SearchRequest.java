package com.dongnebook.domain.book.adapter.in.request;

import lombok.Getter;

@Getter
public class SearchRequest {
	final Double longitude;
	final Double latitude;
	final Integer width;
	final Integer height;
	final Integer sector;
	final Integer level;

	public SearchRequest(Double longitude, Double latitude, Integer width, Integer height, Integer sector,
		Integer level) {
		this.longitude = longitude;
		this.latitude = latitude;
		this.width = width == null ? 1000 : width * 50;
		this.height = height == null ? 1000 : height * 50;
		this.sector = sector;
		this.level = level;
	}
}
