package com.dongnebook.domain.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
		return "이곳의 좌표는 {" + "위도='" + latitude + '\'' + ", 경도='" + longitude + '\'' + "입니다}";
	}

	public static List<Double> latRangeList(Double latitude, Integer length, Integer level) {

		if (Objects.isNull(latitude)) {
			return null;
		}
		// 3x3 6, 4x4 8
		//홀수 일때

		return calculateLatRange(latitude, length, level);
	}

	public static List<Double> lonRangeList(Double longitude, Integer width, Integer level) {

		if (Objects.isNull(longitude)) {
			return null;
		}

		return calculateLonRange(longitude, width, level);

	}

	private static ArrayList<Double> calculateLatRange(Double latitude, Integer length, Integer level) {
		ArrayList<Double> latRangeList = new ArrayList<>();
		double range = ((length / 1.1) / 100000) / (level * 2);
		if (level % 2 == 1) {
			for (int i = level/2+1; i > 0; i--) {
				latRangeList.add(latitude + range * (1 + ((i-1) * 2)));
			}
			for (int i = 1; i <= level/2+1; i++) {
				latRangeList.add(latitude - range * (1 + ((i-1) * 2)));
			}
		} else {
			for (int i = level/2; i > 0; i--) {
				latRangeList.add(latitude + range * (i * 2));
			}
			for (int i = 0; i < level/2; i++) {
				latRangeList.add(latitude - range * (i * 2));
			}
		}
		log.info("latRangeList={}",latRangeList);
		return latRangeList;
	}

	private static ArrayList<Double> calculateLonRange(Double longitude, Integer width, Integer level) {
		ArrayList<Double> lonRangeList = new ArrayList<>();
		double range = ((width / 0.9) / 100000) / (level * 2);
		if (level % 2 == 1) {
			for (int i = level/2 + 1; i > 0; i--) {
				lonRangeList.add(longitude - range * (1 + ((i-1) * 2)));
			}
			for (int i = 1; i <= level/2 + 1; i++) {
				lonRangeList.add(longitude + range * (1 + ((i-1) * 2)));
			}
		} else {
			for (int i = level/2; i > 0; i--) {
				lonRangeList.add(longitude - range * (i * 2));
			}
			for (int i = 0; i < level/2; i++) {
				lonRangeList.add(longitude + range * (i * 2));
			}
		}
		log.info("lonRangeList={}",lonRangeList);
		return lonRangeList;
	}

}

