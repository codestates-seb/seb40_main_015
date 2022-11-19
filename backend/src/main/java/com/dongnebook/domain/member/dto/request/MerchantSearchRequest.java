package com.dongnebook.domain.member.dto.request;


import static com.dongnebook.domain.member.domain.QMember.*;


import com.dongnebook.domain.model.Location;
import com.querydsl.core.types.dsl.BooleanExpression;

import lombok.Getter;

@Getter
public class MerchantSearchRequest {

	private	Double longitude;
	private Double latitude;
	private Integer sector;

	public MerchantSearchRequest(Double longitude, Double latitude, Integer sector) {
		this.longitude = longitude;
		this.latitude = latitude;
		this.sector = sector;
	}

	public BooleanExpression sectorBetween() {
		int count = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				count++;
				if (sector == count) {
					return member.location.latitude.between(Location.latRangeList(latitude).get(i + 1), Location.latRangeList(latitude).get(i))
						.and(member.location.longitude.between(Location.lonRangeList(longitude).get(j), Location.lonRangeList(longitude).get(j + 1)));
				}
			}
		}
		return null;
	}
}
