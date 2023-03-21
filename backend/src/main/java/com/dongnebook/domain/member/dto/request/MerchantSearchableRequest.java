package com.dongnebook.domain.member.dto.request;

import com.dongnebook.domain.book.adapter.in.request.SearchRequest;
import com.dongnebook.domain.book.application.port.in.request.MapSearchable;

import lombok.Getter;

@Getter
public class MerchantSearchableRequest implements MapSearchable {
	private final SearchRequest searchRequest;

	public MerchantSearchableRequest(Double longitude, Double latitude, Integer width, Integer height,
		Integer sector, Integer level) {
		this.searchRequest = new SearchRequest(longitude, latitude, width, height, sector, level);
	}

	@Override
	public Integer getLevel(){
		return searchRequest.getLevel();
	}

	@Override
	public Double getLongitude() {
		return searchRequest.getLongitude();
	}

	@Override
	public Double getLatitude() {
		return searchRequest.getLatitude();
	}

	@Override
	public Integer getWidth() {
		return searchRequest.getWidth();
	}

	@Override
	public Integer getHeight() {
		return searchRequest.getHeight();
	}

	@Override
	public Integer getSector() {
		return searchRequest.getSector();
	}


}
