package com.dongnebook.domain.book.application.port.in.request;

import lombok.Getter;

@Getter
public class SearchCommand {
	final Double longitude;
	final Double latitude;
	final Integer width;
	final Integer height;
	final Integer sector;
	final Integer level;

	public SearchCommand(Double longitude, Double latitude, Integer width, Integer height, Integer sector,
		Integer level) {
		this.longitude = longitude;
		this.latitude = latitude;
		this.width = width;
		this.height = height;
		this.sector = sector;
		this.level = level;
	}
}

