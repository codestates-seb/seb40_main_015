package com.dongnebook.domain.alarm.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dongnebook.domain.alarm.repository.AlarmQueryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlarmReadService {
	private final AlarmQueryRepository alarmQueryRepository;

	@Transactional
	public void readAll(Long memberId){
		alarmQueryRepository.read(memberId);
	}
}
