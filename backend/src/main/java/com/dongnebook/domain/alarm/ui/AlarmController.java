package com.dongnebook.domain.alarm.ui;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.dongnebook.domain.alarm.application.AlarmService;
import com.dongnebook.domain.alarm.dto.AlarmResponse;
import com.dongnebook.global.security.auth.annotation.Login;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AlarmController {
	private final AlarmService alarmService;

	@GetMapping("/alarm")
	public List<AlarmResponse> getAlarm(@Login Long memberId) {
		return alarmService.getMyAlarm(memberId);
	}

	@DeleteMapping("/alarm")
	public void deleteAlarm(@Login Long memberId) {
		alarmService.deleteAllById(memberId);
	}

	@GetMapping(value = "/sub/{memberId}", produces = "text/event-stream")
	public SseEmitter subscribe(
		@RequestParam(value = "lastEventId", required = false, defaultValue = "") String lastEventId,
		@PathVariable Long memberId) {
		return alarmService.sub(memberId, lastEventId);
	}

	@DeleteMapping("/alarm/{alarmId}")
	public void deleteAlarm(@Login Long memberId, @PathVariable Long alarmId) {
		alarmService.deleteAlarmById(memberId, alarmId);
	}
}
