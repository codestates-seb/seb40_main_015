package com.dongnebook.domain.member.dto.request;

import lombok.Getter;

@Getter
public class MerchantSearchRequest {
	private	Double longitude;
	private Double latitude;
	private Integer width;
	private Integer height;
	private Integer sector;
	private Integer level;

	public MerchantSearchRequest(Double longitude, Double latitude, Integer width, Integer height, Integer sector,
		Integer level) {
		this.longitude = longitude;
		this.latitude = latitude;
		this.width = width == null ? 1000 : width * 50;
		this.height = height == null ? 1000 : height * 50;
		this.sector = sector;
		this.level = level;
	}

}
