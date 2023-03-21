package com.dongnebook.domain.book.application.port.in.request;

import java.util.Optional;

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
		this.width = Optional.ofNullable(width).map(w -> w * 50).orElse(1000);
		this.height = Optional.ofNullable(height).map(w -> w * 50).orElse(1000);
		this.sector = sector;
		this.level = level;
	}
}

