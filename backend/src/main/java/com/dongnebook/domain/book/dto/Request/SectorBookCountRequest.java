package com.dongnebook.domain.book.dto.request;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;

@Getter
public class SectorBookCountRequest {

	private String bookTitle;
	private	Double longitude;
	private Double latitude;

	public SectorBookCountRequest(String bookTitle, Double longitude, Double latitude) {
		this.bookTitle = bookTitle;
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public List<Double> latRangeList(){
		Double centralLat = this.latitude;
		return Arrays.asList(centralLat + 0.00227, centralLat + 0.00075, centralLat - 0.00075,
			centralLat - 0.00227);
	}

	public List<Double> lonRangeList(){
		Double centralLon = this.longitude;
		return Arrays.asList(centralLon - 0.00277, centralLon - 0.00093, centralLon + 0.00093,
			centralLon + 0.00277);
	}
}
