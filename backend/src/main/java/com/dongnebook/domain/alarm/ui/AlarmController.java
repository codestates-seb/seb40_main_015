package com.dongnebook.domain.alarm.ui;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
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

	@DeleteMapping("/alarm")
	public void deleteAlarm(@Login AuthMember authMember){
		alarmService.deleteAllById(authMember.getMemberId());
	}

	@GetMapping(value = "/sub/{memberId}", produces = "text/event-stream")
	public SseEmitter subscribe(
		@RequestParam(value = "lastEventId", required = false, defaultValue = "" ) String lastEventId,
		@PathVariable Long memberId){

	return alarmService.sub(memberId, lastEventId);
	}

	@DeleteMapping("/alarm/{alarmId}")
	public void deleteAlarm(@Login AuthMember authMember, @PathVariable Long alarmId){
		alarmService.deleteAlarmById(authMember.getMemberId(),alarmId);

	}

}
