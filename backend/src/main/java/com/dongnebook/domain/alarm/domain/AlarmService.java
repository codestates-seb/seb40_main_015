package com.dongnebook.domain.alarm.domain;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.dongnebook.domain.alarm.dto.AlarmResponse;
import com.dongnebook.domain.alarm.exception.AlarmNotFound;
import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.global.error.exception.NotOwnerException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AlarmService {

	private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

	private final AlarmQueryRepository alarmQueryRepository;
	private final AlarmRepository alarmRepository;
	private final AlarmReadService alarmReadService;
	private final EmitterRepositoryImpl emitterRepository;

	/**
	 * 현재 로그인한 사용자의 모든 알람을 DTO로 가져온다.
	 * 사용자의 모든 알람을 읽음 처리 시킨다.
	 *
	 * @return
	 */
	@Transactional
	public void sendAlarm(Member member, Book book, AlarmType type) {
		Alarm alarm = Alarm.create(member, book, type);
		alarmRepository.save(alarm);

		Map<String, SseEmitter> sseEmitters = emitterRepository.findAllEmitterStartWithByMemberId(member.getId());
		String eventId = String.valueOf(member.getId()).concat("_").concat(String.valueOf(System.currentTimeMillis()));
		sseEmitters.forEach(
			(key, emitter) -> {
				// 데이터 캐시 저장(유실된 데이터 처리하기 위함)
				emitterRepository.saveEventCache(key, alarm);
				// 데이터 전송
				sendToClient(emitter, eventId, "알람이 도착했습니다");
			}
		);

	}

	@Transactional
	public List<AlarmResponse> getMyAlarm(Long memberId) {
		List<AlarmResponse> myAlarm = alarmQueryRepository.getMyAlarm(memberId);
		alarmReadService.readAll(memberId);

		return myAlarm;
	}

	public SseEmitter sub(Long memberId, String lastEventId) {

		String id = String.valueOf(memberId).concat("_").concat(String.valueOf(System.currentTimeMillis()));
		SseEmitter emitter = emitterRepository.save(id, new SseEmitter(DEFAULT_TIMEOUT));

		emitter.onCompletion(() -> emitterRepository.deleteById(id)); //네트워크 오류
		emitter.onTimeout(() -> emitterRepository.deleteById(id)); //시간 초과
		emitter.onError((e) -> emitterRepository.deleteById(id)); //오류

		try {
			emitter.send(SseEmitter.event()
				.id(id)
				.name("sse")
				.data("EventStream Created. [memberId=" + memberId + "]"));
		} catch (IOException exception) {
			emitterRepository.deleteById(id);
			throw new RuntimeException("연결 오류!");
		}

		if (!lastEventId.isEmpty()) {
			Map<String, Object> events = emitterRepository.findAllEventCacheStartWithByMemberId(memberId);
			events.entrySet().stream()
				.filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
				.forEach(entry -> sendToClient(emitter, entry.getKey(), entry.getValue()));
		}

		return emitter;
	}

	private void sendToClient(SseEmitter emitter, String id, Object data) {
		try {
			emitter.send(SseEmitter.event()
				.id(id)
				.name("sse")
				.data(data));
		} catch (IOException exception) {
			emitterRepository.deleteById(id);
			throw new RuntimeException("연결 오류!");
		}
	}

	@Transactional
	public void deleteAlarm(Long memberId, Long alarmId) {
		Alarm alarm = alarmQueryRepository.findByAlarmWithMemberId(alarmId).orElseThrow(AlarmNotFound::new);
		if (Objects.equals(alarm.getMember().getId(), memberId)) {
			alarmRepository.delete(alarm);
			return;
		}
		throw new NotOwnerException();
	}
}


