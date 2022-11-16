package com.dongnebook.domain.book.dto.response;

import com.dongnebook.domain.model.Location;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookSectorCountResponse {
	private Long totalBookCount;
	private Long sector;
	private Location location;

	public void plusBookCount(){
		if(totalBookCount==null){
			this.totalBookCount = 0L;
		}
		this.totalBookCount++;
	}

	public void initLocation(Location location) {
		this.location = location;
	}

	public void initSector(Long sector) {
		this.sector = sector;
	}
}
