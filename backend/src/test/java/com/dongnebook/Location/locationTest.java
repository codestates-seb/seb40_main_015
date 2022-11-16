package com.dongnebook.Location;

import org.junit.jupiter.api.Test;

import com.dongnebook.domain.model.Location;

public class locationTest {


	@Test
	public void Test(){
		Location location = Location.builder()
			.latitude(127.10854722)
			.longitude(37.57921667)
			.build();

		Location.DMSLocation dmsLocation = location.parseDMS(location);
		System.out.println("dmsLocation = " + dmsLocation.toString());
	}
}
