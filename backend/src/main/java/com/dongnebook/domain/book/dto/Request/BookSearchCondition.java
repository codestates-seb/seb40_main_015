package com.dongnebook.domain.book.dto.request;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;

@Getter
public class BookSearchCondition {

	private String bookTitle;
	private	Double longitude;
	private Double latitude;
	private Integer sector;

	public BookSearchCondition(String bookTitle, Double longitude, Double latitude, Integer sector) {
		this.bookTitle = bookTitle;
		this.longitude = longitude;
		this.latitude = latitude;
		this.sector = sector;
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
