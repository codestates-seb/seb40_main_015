package com.dongnebook.support;

import com.dongnebook.domain.model.Location;

import lombok.Getter;

@Getter
public enum LocationStub {
	//섹터5
	봉천역(37.482475661,126.941669283),
	복마니정육점(37.482331,126.939966),
	삼성전자봉천역점(37.482729,126.941385),
	다이소봉천점(37.482517,126.942409),
	목포부부아구찜(37.481951,126.941932),
	스타벅스봉천점(37.482023,126.942231),
	롯데리아봉천점(37.4822,126.9416),
	농협은행봉천점(37.482426,126.941100),
	//섹터9
	관악구청(37.478237,126.951522),
	//섹터7
	서원동성당(37.478476,126.933436),
	//섹터2
	서울관광고(37.487996,126.943394);

	private Double latitude;
	private Double longitude;

	LocationStub(Double latitude, Double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public Location of(){
		return Location.builder()
			.latitude(this.latitude)
			.longitude(this.longitude)
			.build();
	}
}
