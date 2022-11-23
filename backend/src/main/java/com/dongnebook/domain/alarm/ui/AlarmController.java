package com.dongnebook.domain.alarm.ui;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.dongnebook.domain.alarm.domain.AlarmService;
import com.dongnebook.domain.alarm.dto.AlarmResponse;
import com.dongnebook.global.Login;

import com.dongnebook.global.config.security.auth.userdetails.AuthMember;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AlarmController {

	private final AlarmService alarmService;

	@GetMapping("/alarm")
	public List<AlarmResponse> getAlarm(@Login AuthMember authMember){
		return alarmService.getMyAlarm(authMember.getMemberId());
	}

	@GetMapping(value = "/sub", produces = "text/event-stream")
	public SseEmitter subscribe(@Login AuthMember authMember,
		@RequestHeader(value = "LastEventId", required = false, defaultValue = "" ) String lastEventId){

	return alarmService.sub(authMember.getMemberId(), lastEventId);
	}

}
