package com.dongnebook.domain.member.dto.response;

import com.dongnebook.domain.model.Location;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MerchantSectorCountResponse {
	private Long merchantCount;
	private final Integer sector;
	private final Location location;

	public MerchantSectorCountResponse increaseMerchantCount(){
		this.merchantCount++;
		return this;
	}

	public MerchantSectorCountResponse(Integer sector, Location location) {
		this.merchantCount = 0L;
		this.sector = sector;
		this.location = location;
	}
}
