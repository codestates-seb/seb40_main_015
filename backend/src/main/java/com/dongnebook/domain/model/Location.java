package com.dongnebook.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Location {

	@Column(name = "latitude")
	private Double latitude;

	@Column(name = "longitude")
	private Double longitude;

	@Builder
	public Location(Double latitude, Double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "이곳의 좌표는 {" +
			"위도='" + latitude + '\'' +
			", 경도='" + longitude + '\'' +
			"입니다}";
	}

	public DMSLocation parseDMS(Location location) {
		/**
		 도 :  소수점 좌표 값의 정수(37)
		 분 : 37을 제외한 0.397 X 60 = 23.82 에서 소수점 앞의 정수(23)
		 초 : 0.82 X 60 = 49.2 에서 소수점 포함 앞의 4자리(49.2)
		 **/
		Long latDegree = getDegree(this.latitude);
		Long latMinutes = getMinutes(this.latitude, latDegree);
		Double latSeconds = getSeconds(this.latitude,latDegree, latMinutes);
		Long lonDegree = getDegree(this.longitude);
		Long lonMinutes = getMinutes(this.longitude, lonDegree);
		Double lonSeconds = getSeconds(this.longitude,lonDegree, lonMinutes);

		return DMSLocation.builder().
			latDegree(latDegree)
			.latMinutes(latMinutes)
			.latSeconds(latSeconds)
			.lonDegree(lonDegree)
			.lonMinutes(lonMinutes)
			.lonSeconds(lonSeconds)
			.build();
	}

	private long getDegree(Double lonOrLat) {
		return lonOrLat.longValue();
	}

	private long getMinutes(Double lonOrLat, Long degree) {


		return (long)((lonOrLat-degree)*60);
	}

	private double getSeconds(Double lonOrLat, Long degree, Long minutes) {
		return (((lonOrLat - degree) * 60) - minutes)*60;
	}

	@ToString
	static public class DMSLocation {

		Long latDegree;
		Long latMinutes;
		Double latSeconds;

		Long lonDegree;
		Long lonMinutes;
		Double lonSeconds;

		@Builder
		public DMSLocation(Long latDegree, Long latMinutes, Double latSeconds, Long lonDegree, Long lonMinutes,
			Double lonSeconds) {
			this.latDegree = latDegree;
			this.latMinutes = latMinutes;
			this.latSeconds = latSeconds;
			this.lonDegree = lonDegree;
			this.lonMinutes = lonMinutes;
			this.lonSeconds = lonSeconds;
		}
	}

}

