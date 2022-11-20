package com.dongnebook.domain.book.dto.request;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import lombok.Getter;

@Getter
public class BookSearchCondition {

	private final String bookTitle;
	private final Double longitude;
	private final Double latitude;
	private final Integer sector;

	public BookSearchCondition(String bookTitle, Double longitude, Double latitude, Integer sector) {
		this.bookTitle = bookTitle == null ? "" : bookTitle;
		this.longitude = longitude;
		this.latitude = latitude;
		this.sector = sector;
	}


	public List<Double> latRangeList(){
		if (Objects.isNull(this.latitude)) {
			return null;
		}
		double centralLat = this.latitude;

		return Arrays.asList(centralLat + 0.00454, centralLat + 0.00151, centralLat - 0.00151,
			centralLat - 0.00227);
	}

	public List<Double> lonRangeList(){
		if (Objects.isNull(this.longitude)) {
			return null;
		}
		double centralLon = this.longitude;
		return Arrays.asList(centralLon - 0.00554, centralLon - 0.00186, centralLon + 0.00186,
			centralLon + 0.00554);
	}
}
