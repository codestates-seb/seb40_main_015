package com.dongnebook.domain.alarm.repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
@NoArgsConstructor
public class EmitterRepositoryImpl implements EmitterRepository {
	private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
	private final Map<String, Object> eventCache = new ConcurrentHashMap<>();

	@Override
	public SseEmitter save(String emitterId, SseEmitter sseEmitter) {
		emitters.put(emitterId, sseEmitter);
		return sseEmitter;
	}

	@Override
	public void saveEventCache(String eventCacheId, Object event) {
		eventCache.put(eventCacheId, event);
	}

	@Override
	public Map<String, SseEmitter> findAllEmitterStartWithMemberId(Long memberId) {
		return emitters.entrySet()
			.stream() //여러개의 Emitter가 존재할 수 있기떄문에 stream 사용
			.filter(entry -> entry.getKey().startsWith(String.valueOf(memberId)))
			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	@Override
	public Map<String, SseEmitter> findAllEmitterStartWithMemberIdInList(List<Long> memberId) {
		return Collections.emptyMap();
	}

	@Override
	public Map<String, Object> findAllEventCacheStartWithMemberId(Long memberId) {
		return emitters.entrySet()
			.stream()
			.filter(entry -> entry.getKey().startsWith(String.valueOf(memberId)))
			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	@Override
	public void deleteById(String id) {
		emitters.remove(id);
	}

	@Override
	public void deleteAllEmitterStartWithMemberId(Long memberId) {
		emitters.forEach((key, emitter) -> {
			if (key.startsWith(String.valueOf(memberId))) {
				emitters.remove(key);
			}
		});
	}

	@Override
	public void deleteAllEventCacheStartWithMemberId(Long memberId) {
		emitters.forEach((key, emitter) -> {
			if (key.startsWith(String.valueOf(memberId))) {
				emitters.remove(key);
			}
		});
	}
}
