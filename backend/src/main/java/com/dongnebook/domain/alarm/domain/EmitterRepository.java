package com.dongnebook.domain.alarm.domain;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface EmitterRepository{
	SseEmitter save(String emitterId, SseEmitter sseEmitter); //Emitter 저장

	void saveEventCache(String eventCacheId, Object event); //이벤트 저장

	Map<String, SseEmitter> findAllEmitterStartWithByMemberId(Long memberId); //해당 회원과  관련된 모든 Emitter를 찾는다

	Map<String, SseEmitter> findAllEmitterStartWithByMemberIdInList(List<Long> memberId); //List 에서 해당 회원과  관련된 모든 Emitter를 찾는다(미 개발)

	Map<String, Object> findAllEventCacheStartWithByMemberId(Long memberId); //해당 회원과관련된 모든 이벤트를 찾는다

	void deleteById(String id); //Emitter를 지운다

	void deleteAllEmitterStartWithMemberId(Long memberId); //해당 회원과 관련된 모든 Emitter를 지운다

	void deleteAllEventCacheStartWithMemberId(Long memberId); //해당 회원과 관련된 모든 이벤트를 지운다
}
