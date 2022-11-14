package com.dongnebook.domain.helloSample.ui;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	@GetMapping("/hello")
	public Map<String, String> Hello() {
		Map<String, String> sampleJson = new HashMap<String, String>();
		sampleJson.put("ServerStatus", "Active");
		sampleJson.put("Msg", "Hello 동네북입니다");
		return sampleJson;
	}
}
