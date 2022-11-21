package com.dongnebook.domain.member.dto.request;


import static com.dongnebook.domain.member.domain.QMember.*;

import java.util.List;

import com.dongnebook.domain.model.Location;
import com.querydsl.core.types.dsl.BooleanExpression;

import lombok.Getter;

@Getter
public class MerchantSearchRequest {

	private	Double longitude;
	private Double latitude;
	private Integer width;
	private Integer length;
	private Integer sector;
	private Integer level;

	public MerchantSearchRequest(Double longitude, Double latitude, Integer width, Integer length, Integer sector,
		Integer level) {
		this.longitude = longitude;
		this.latitude = latitude;
		this.width = width == null ? 1000 : width * 50;
		this.length = length == null ? 1000 : length * 50;
		this.sector = sector;
		this.level = level;
	}

	public BooleanExpression sectorBetween() {
		List<Double> latRange = Location.latRangeList(latitude, length,level);
		List<Double> lonRange = Location.lonRangeList(longitude, width,level);
		int count = 0;
		for (int i = 0; i < level; i++) {
			for (int j = 0; j < level; j++) {
				count++;
				if (sector == count) {
					return member.location.latitude.between(latRange.get(i + 1), latRange.get(i))
						.and(member.location.longitude.between(lonRange.get(j), lonRange.get(j + 1)));
				}
			}
		}
		return null;
	}
}
