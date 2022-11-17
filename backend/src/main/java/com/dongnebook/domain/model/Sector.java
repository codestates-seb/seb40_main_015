package com.dongnebook.domain.model;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;

@Getter
public enum Sector {
	SECTOR1(2.4,32.2,123.12,421.3);


	private Double latFrom;
	private Double latTo;
	private Double lonFrom;
	private Double lonTo;

	Sector(Double latFrom, Double latTo, Double lonFrom, Double lonTo) {
		this.latFrom = latFrom;
		this.latTo = latTo;
		this.lonFrom = lonFrom;
		this.lonTo = lonTo;
	}
	//
	// public List<Double> latRangeList(){
	// 	Double centralLat = this.latitude;
	// 	return Arrays.asList(centralLat + 0.00227, centralLat + 0.00075, centralLat - 0.00075,
	// 		centralLat - 0.00227);
	// }
	//
	// public List<Double> lonRangeList(){
	// 	Double centralLon = this.longitude;
	// 	return Arrays.asList(centralLon - 0.00277, centralLon - 0.00093, centralLon + 0.00093,
	// 		centralLon + 0.00277);
	// }
	// 섹터 1 :  LatRange 1~0,LonRange 0~1,
	// 섹터 2 :  LatRange 1~0,LonRange 1~2,
	// 섹터 3 :  LatRange 1~0,LonRange 2~3,
	// 섹터 4 : LatRange 2~1,LonRange 0~1,
	// 섹터 5 :  LatRange 2~1,LonRange 1~2,
	// 섹터 6 :  LatRange 2~1,LonRange 2~3,
	// 섹터 7 :  LatRange 3~2,LonRange 0~1,
	// 섹터 8 :  LatRange 3~2,LonRange 1~2,
	// 섹터 9 :  LatRange 3~2,LonRange 2~3,
}
