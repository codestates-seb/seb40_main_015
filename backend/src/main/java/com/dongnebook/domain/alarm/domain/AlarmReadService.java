package com.dongnebook.domain.alarm.domain;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
