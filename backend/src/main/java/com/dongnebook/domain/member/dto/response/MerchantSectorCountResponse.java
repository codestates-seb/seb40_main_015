package com.dongnebook.domain.member.dto.response;

import com.dongnebook.domain.model.Location;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MerchantSectorCountResponse {

	private Long merchantCount;
	private Long sector;
	private Location location;

	public void plusBookCount(){
		if(merchantCount ==null){
			this.merchantCount = 0L;
		}
		this.merchantCount++;
	}

	public void initLocation(Location location) {
		this.location = location;
	}

	public void initSector(Long sector) {
		this.sector = sector;
	}
}
