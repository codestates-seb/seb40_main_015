package com.dongnebook.domain.book.application.port.in.request;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SearchCommand implements Serializable {
	Double longitude;
	Double latitude;
	Integer width;
	Integer height;
	Integer sector;
	Integer level;

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

