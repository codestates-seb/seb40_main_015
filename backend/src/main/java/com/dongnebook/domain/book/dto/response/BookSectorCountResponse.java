package com.dongnebook.domain.book.dto.response;

import com.dongnebook.domain.model.Location;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookSectorCountResponse {
	private Long bookCount;
	private Integer sector;
	private Location location;

	public void plusBookCount(){
		if(bookCount ==null){
			this.bookCount = 0L;
		}
		this.bookCount++;
	}

	public void initLocation(Location location) {
		this.location = location;
	}

	public void initSector(Integer sector) {
		this.sector = sector;
	}
}
