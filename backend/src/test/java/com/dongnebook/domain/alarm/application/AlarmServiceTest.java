package com.dongnebook.domain.alarm.application;

import com.dongnebook.domain.alarm.domain.Alarm;
import com.dongnebook.domain.alarm.dto.AlarmResponse;
import com.dongnebook.domain.alarm.exception.AlarmNotFoundException;
import com.dongnebook.domain.alarm.repository.AlarmQueryRepository;
import com.dongnebook.domain.alarm.repository.AlarmRepository;
import com.dongnebook.domain.alarm.repository.EmitterRepositoryImpl;
import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.book.domain.BookProduct;
import com.dongnebook.domain.book.domain.Money;
import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.model.Location;
import com.dongnebook.global.enums.AlarmType;
import com.dongnebook.global.error.exception.NotOwnerException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AlarmServiceTest {
    @Mock
    private AlarmQueryRepository alarmQueryRepository;
    @Mock
    private AlarmRepository alarmRepository;
    @Mock
    private AlarmReadService alarmReadService;
    @Mock
    private EmitterRepositoryImpl emitterRepository;
    @InjectMocks
    private AlarmService alarmService;


    @Test
    public void sendAlarmTest() throws Exception {
        // given
        Member member = new Member(1L, "test1", "tester1", new Location(37.5340, 126.7064), "anywhere", "abc1@abc.com");
        Book book = new Book(2L, new BookProduct(), "aaa@abc.com", "기본이 중요", Money.of(1000), new Location(37.5340, 126.7064), member);
        given(alarmRepository.save(any())).willReturn(any());

        SseEmitter sseEmitter = new SseEmitter(30L);
        Map<String, SseEmitter> map = new HashMap<>();
        map.put("3", sseEmitter);

        given(emitterRepository.findAllEmitterStartWithMemberId(member.getId())).willReturn(map);

        doNothing().when(emitterRepository).saveEventCache(any(), any());

        // when & then
        assertDoesNotThrow(() -> alarmService.sendAlarm(member, book, AlarmType.RENTAL));
    }

    @Test
    public void getMyAlarmTest() throws Exception {
        // given
        Long memberId = 1L;
        List<AlarmResponse> alarmResponseList = List.of(
                new AlarmResponse(
                        1L, AlarmType.MESSAGE, memberId, "JAVA의 정석", true
                )
        );
        given(alarmQueryRepository.getMyAlarm(any())).willReturn(alarmResponseList);
        doNothing().when(alarmReadService).readAll(any());

        // when
        List<AlarmResponse> returnedAlarmResponseList = alarmService.getMyAlarm(memberId);

        // then
        assertEquals(returnedAlarmResponseList.get(0).getAlarmId(), alarmResponseList.get(0).getAlarmId());
    }

    @Test
    public void deleteAllByIdTest() throws Exception {
        // given
        Long memberId = 1L;
        doNothing().when(alarmQueryRepository).deleteAllByMemberId(any());

        // when
        alarmService.deleteAllById(memberId);

        // then
        verify(alarmQueryRepository, times(1)).deleteAllByMemberId(memberId);
    }

    @Test
    public void AlarmNotFoundExceptionTest() throws Exception {
        // given
        Long memberId = 1L;
        Long alarmId = 1L;

        Optional<Alarm> alarmOptional = Optional.empty();
        given(alarmQueryRepository.findByAlarmWithMemberId(any())).willReturn(alarmOptional);

        // when & then
        assertThrows(AlarmNotFoundException.class, () -> alarmService.deleteAlarmById(memberId, alarmId));
    }

    @Test
    public void NotOwnerExceptionTest() throws Exception {
        // given
        Long memberId = 2L;
        Long alarmId = 1L;

        Member member = new Member(1L, "test1", "tester1", new Location(37.5340, 126.7064), "anywhere", "abc1@abc.com");
        Book book = new Book(2L, new BookProduct(), "aaa@abc.com", "기본이 중요", Money.of(1000), new Location(37.5340, 126.7064), member);
        Alarm alarm = Alarm.create(member, book, AlarmType.RETURN);
        Optional<Alarm> alarmOptional = Optional.of(alarm);
        given(alarmQueryRepository.findByAlarmWithMemberId(any())).willReturn(alarmOptional);

        // when & then
        assertThrows(NotOwnerException.class, () -> alarmService.deleteAlarmById(memberId, alarmId));
    }

    @Test
    public void deleteAlarmByIdTest() throws Exception {
        // given
        Long memberId = 1L;
        Long alarmId = 1L;

        Member member = new Member(memberId, "test1", "tester1", new Location(37.5340, 126.7064), "anywhere", "abc1@abc.com");
        Book book = new Book(2L, new BookProduct(), "aaa@abc.com", "기본이 중요", Money.of(1000), new Location(37.5340, 126.7064), member);
        Alarm alarm = Alarm.create(member, book, AlarmType.RETURN);
        Optional<Alarm> alarmOptional = Optional.of(alarm);
        given(alarmQueryRepository.findByAlarmWithMemberId(any())).willReturn(alarmOptional);
        doNothing().when(alarmRepository).delete(any());

        // when & then
        assertDoesNotThrow(() -> alarmService.deleteAlarmById(memberId, alarmId));
    }

    @Test
    public void subTest1() throws Exception {
        // given
        Long memberId = 1L;
        String lastEventId = "";

        SseEmitter sseEmitter = new SseEmitter(30L);
        given(emitterRepository.save(any(), any())).willReturn(sseEmitter);

        // when
        SseEmitter returnedEmitter = alarmService.sub(memberId, lastEventId);

        // then
        assertEquals(returnedEmitter.getTimeout(), sseEmitter.getTimeout());
    }

    @Test
    public void subTest2() throws Exception {
        // given
        Long memberId = 1L;
        String lastEventId = "1";

        SseEmitter sseEmitter = new SseEmitter(30L);
        given(emitterRepository.save(any(), any())).willReturn(sseEmitter);

        Map<String, Object> map = new HashMap<>();
        map.put("1", -1);
        given(emitterRepository.findAllEventCacheStartWithMemberId(any())).willReturn(map);

        // when
        SseEmitter returnedEmitter = alarmService.sub(memberId, lastEventId);

        // then
        assertEquals(returnedEmitter.getTimeout(), sseEmitter.getTimeout());
    }

    @Test
    public void subTest3() throws Exception {
        // given
        Long memberId = 1L;
        String lastEventId = "0";

        SseEmitter sseEmitter = new SseEmitter(30L);
        given(emitterRepository.save(any(), any())).willReturn(sseEmitter);

        Map<String, Object> map = new HashMap<>();
        map.put("1", 5);
        given(emitterRepository.findAllEventCacheStartWithMemberId(any())).willReturn(map);

        // when
        SseEmitter returnedEmitter = alarmService.sub(memberId, lastEventId);

        // then
        assertEquals(returnedEmitter.getTimeout(), sseEmitter.getTimeout());
    }
}
