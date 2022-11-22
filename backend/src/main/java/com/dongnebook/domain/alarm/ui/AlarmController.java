package com.dongnebook.domain.alarm.ui;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dongnebook.global.Login;
import com.dongnebook.global.config.security.auth.userdetails.AuthMember;

@RestController
public class AlarmController {

	@GetMapping("/alarm")
	public void getAlarm(@Login AuthMember authMember){


	}
}
