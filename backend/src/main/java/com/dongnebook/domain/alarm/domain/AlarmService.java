package com.dongnebook.domain.alarm.domain;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dongnebook.domain.alarm.dto.AlarmResponse;
import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.member.domain.Member;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AlarmService {

	private final AlarmQueryRepository alarmQueryRepository;
	private final AlarmRepository alarmRepository;
	private final AlarmReadService alarmReadService;

	/**
	 * 현재 로그인한 사용자의 모든 알람을 DTO로 가져온다.
	 * 사용자의 모든 알람을 읽음 처리 시킨다.
	 *
	 * @return
	 */
	@Transactional
	public void sendAlarm(Member member, Book book,AlarmType type){
		Alarm alarm = Alarm.create(member, book, type);
		alarmRepository.save(alarm);

	}
	@Transactional
	public List<AlarmResponse> getMyAlarm(Long memberId) {
		List<AlarmResponse> myAlarm = alarmQueryRepository.getMyAlarm(memberId);
		alarmReadService.readAll(memberId);

		return myAlarm;
	}
}
