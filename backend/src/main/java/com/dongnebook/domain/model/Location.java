package com.dongnebook.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.dongnebook.global.dto.request.MapSearchable;
import com.querydsl.core.types.dsl.BooleanExpression;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Location implements Serializable {
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

	public static List<Double> latRangeList(Double centralLatitude, Integer length, Integer level) {
		if (Objects.isNull(centralLatitude)) {
			return Collections.emptyList();
		}

		return calculateLatRange(centralLatitude, length, level);
	}

	public static List<Double> lonRangeList(Double centralLongitude, Integer width, Integer level) {
		if (Objects.isNull(centralLongitude)) {
			return Collections.emptyList();
		}

		return calculateLonRange(centralLongitude, width, level);
	}

	private static List<Double> calculateLatRange(Double latitude, Integer length, Integer level) {
		List<Double> latRangeList = new ArrayList<>();
		double range = ((length / 1.1) / 100000) / (level * 2);

		if (level % 2 == 1) {

			for (int i = level / 2 + 1; i > 0; i--) {
				latRangeList.add(latitude + range * (1 + ((i - 1) * 2)));
			}

			for (int i = 1; i <= level / 2 + 1; i++) {
				latRangeList.add(latitude - range * (1 + ((i - 1) * 2)));
			}

		} else {

			for (int i = level / 2; i > 0; i--) {
				latRangeList.add(latitude + range * (i * 2));
			}

			for (int i = 0; i <= level / 2; i++) {
				latRangeList.add(latitude - range * (i * 2));
			}

		}

		log.info("latRangeList={}", latRangeList);
		return latRangeList;
	}

	private static List<Double> calculateLonRange(Double longitude, Integer width, Integer level) {
		List<Double> lonRangeList = new ArrayList<>();
		double range = ((width / 0.9) / 100000) / (level * 2);

		if (level % 2 == 1) {

			for (int i = level / 2 + 1; i > 0; i--) {
				lonRangeList.add(longitude - range * (1 + ((i - 1) * 2)));
			}

			for (int i = 1; i <= level / 2 + 1; i++) {
				lonRangeList.add(longitude + range * (1 + ((i - 1) * 2)));
			}

		} else {

			for (int i = level / 2; i > 0; i--) {
				lonRangeList.add(longitude - range * (i * 2));
			}

			for (int i = 0; i <= level / 2; i++) {
				lonRangeList.add(longitude + range * (i * 2));
			}

		}
		log.info("lonRangeList={}", lonRangeList);
		return lonRangeList;
	}

	public static BooleanExpression inSector(Double currentLatitude, Double currentLongitude, Integer height,
		Integer width, Integer givenSector, Integer level, QLocation qLocation) {
		List<Double> latRangeList = Location.latRangeList(currentLatitude, height, level);
		List<Double> lonRangeList = Location.lonRangeList(currentLongitude, width, level);

		if (Objects.isNull(latRangeList) || Objects.isNull(lonRangeList) || Objects.isNull(givenSector)) {
			return null;
		}

		int sector = 0;
		for (int i = 0; i < level; i++) {

			for (int j = 0; j < level; j++) {
				sector++;

				if (givenSector == sector) {
					return qLocation.latitude.between(latRangeList.get(i + 1), latRangeList.get(i))
						.and(qLocation.longitude.between(lonRangeList.get(j), lonRangeList.get(j + 1)));
				}

			}

		}
		return null;
	}

	public boolean checkRange(MapSearchable condition, int sector) {
		List<Double> latRangeList = latRangeList(condition.getLatitude(), condition.getHeight(), condition.getLevel());
		List<Double> lonRangeList = lonRangeList(condition.getLongitude(), condition.getWidth(), condition.getLevel());
		int i = (sector - 1) / condition.getLevel();
		int j = (sector - 1) % condition.getLevel();

		return latRangeList.get(i + 1) <= this.latitude && this.latitude <= latRangeList.get(i)
			&& lonRangeList.get(j) <= this.longitude && this.longitude <= lonRangeList.get(
			j + 1);
	}
}

