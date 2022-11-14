package com.dongnebook.domain;

import java.time.LocalDateTime;

import org.springframework.boot.test.context.SpringBootTest;


public class test {

	public void test() {
		LocalDateTime localDateTime = LocalDateTime.now();
		LocalDateTime localDateTime1 = localDateTime.plusDays(9);
		System.out.println("localDateTime1 = " + localDateTime1);
	}

}
