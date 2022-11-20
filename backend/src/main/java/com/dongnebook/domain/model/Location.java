package com.dongnebook.domain.model;

import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Location {

	@Column(name = "latitude")
	private Double latitude;

	@Column(name = "longitude")
	private Double longitude;

	@Builder
	public Location(Double latitude, Double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "이곳의 좌표는 {" +
			"위도='" + latitude + '\'' +
			", 경도='" + longitude + '\'' +
			"입니다}";
	}


	public List<Double> latRangeList(){
		Double centralLat = this.latitude;
		return Arrays.asList(centralLat + 0.00454, centralLat + 0.00151, centralLat - 0.00151,
			centralLat - 0.00454);
	}

	public List<Double> lonRangeList(){
		Double centralLon = this.longitude;
		return Arrays.asList(centralLon - 0.00554, centralLon - 0.00186, centralLon + 0.00186,
			centralLon + 0.00554);
	}

	public static List<Double> latRangeList(Double latitude){
		return Arrays.asList(latitude + 0.00454, latitude + 0.00151, latitude - 0.00151,
			latitude - 0.00454);
	}

	public static List<Double> lonRangeList(Double longitude){
		return Arrays.asList(longitude - 0.00554, longitude - 0.00186, longitude + 0.00186,
			longitude + 0.00554);
	}





}

