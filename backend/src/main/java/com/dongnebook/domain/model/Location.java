package com.dongnebook.domain.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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


	// public static List<Double> latRangeList(Double latitude){
	// 	return Arrays.asList(latitude + 0.00454, latitude + 0.00151, latitude - 0.00151,
	// 		latitude - 0.00454);
	// }
	//
	// public static List<Double> lonRangeList(Double longitude){
	// 	return Arrays.asList(longitude - 0.00554, longitude - 0.00186, longitude + 0.00186,
	// 		longitude + 0.00554);
	// }

	public static List<Double> latRangeList(Double latitude,Integer length) {

		if (Objects.isNull(latitude)) {
			return null;
		}

		double range = ((length / 1.1)/100000) / 6;

		return Arrays.asList(latitude + (range * 3), latitude + range, latitude - range,
			latitude - range * 3);
	}

	public static List<Double> lonRangeList(Double longitude, Integer width) {

		if (Objects.isNull(longitude)) {
			return null;
		}

		double range = ((width / 0.9)/100000) / 6;

		return Arrays.asList(longitude - (range * 3), longitude - range, longitude + range,
			longitude + range * 3);
	}





}

