package com.dongnebook.domain.book.dto.response;

import com.dongnebook.domain.model.Location;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookCountPerSectorResponse {

	private Long bookCount;
	private final Integer sector;
	private final Location location;

	public BookCountPerSectorResponse increaseBookCount(){
		this.bookCount++;
		return this;
	}

	public BookCountPerSectorResponse(Long bookCount, Integer sector, Location location) {
		this.bookCount = bookCount;
		this.sector = sector;
		this.location = location;
	}

	@Override
	public String toString() {
		return "{" +
			"bookCount=" + bookCount +
			", sector=" + sector +
			", location=" + location +
			'}';
	}
}
