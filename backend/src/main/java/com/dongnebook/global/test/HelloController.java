package com.dongnebook.global.test;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class HelloController {

	String currentTime = LocalDateTime.now().toString();

	@GetMapping
	public ResponseEntity<String> getHello() {
		return new ResponseEntity<>(currentTime, HttpStatus.OK);
	}
}
