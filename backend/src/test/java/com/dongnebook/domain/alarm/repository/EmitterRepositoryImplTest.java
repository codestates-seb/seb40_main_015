package com.dongnebook.domain.alarm.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.dongnebook.domain.alarm.domain.Alarm;
import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.book.domain.BookProduct;
import com.dongnebook.domain.book.domain.Money;
import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.model.Location;
import com.dongnebook.global.enums.AlarmType;

class EmitterRepositoryImplTest {
    private EmitterRepositoryImpl emitterRepository;
    private final Long DEFAULT_TIMEOUT = 60L * 1000L * 60L;

    @BeforeEach
    public void setup() {
        emitterRepository = new EmitterRepositoryImpl();
    }


    @Test
    void saveTest() throws Exception {
        // given
        Long memberId = 1L;
        String emitterId =  memberId + "_" + System.currentTimeMillis();
        SseEmitter sseEmitter = new SseEmitter(DEFAULT_TIMEOUT);

        // when & then
        Assertions.assertDoesNotThrow(() -> emitterRepository.save(emitterId, sseEmitter));
    }

    @Test
    void saveEventCacheTest() throws Exception {
        // given
        Long memberId = 1L;
        String eventCacheId =  memberId + "_" + System.currentTimeMillis();

        Member member = new Member(1L, "test", "tester", new Location(37.5340, 126.7064), "abcd", "aaa@aa.com");
        BookProduct bookProduct1 = new BookProduct("수학의 정석1", "남궁성", "도우출판");
        Book book = Book.builder()
                .id(1L)
                .bookProduct(bookProduct1)
                .imgUrl("aaa@abc.com")
                .description("기본이 중요")
                .rentalFee(Money.of(1000))
                .location(new Location(37.5340, 126.7064))
                .member(member)
                .build();
        Alarm alarm = new Alarm(member, book, AlarmType.MESSAGE, false);

        // when & then
        Assertions.assertDoesNotThrow(() -> emitterRepository.saveEventCache(eventCacheId, alarm));
    }

    @Test
    void testFindAllEmitterStartWithMemberId() {
        // Given
        Long memberId = 1L;
        String emitterId1 = memberId + "_" + System.currentTimeMillis();
        emitterRepository.save(emitterId1, new SseEmitter(DEFAULT_TIMEOUT));
        String emitterId2 = "2_" + System.currentTimeMillis();
        emitterRepository.save(emitterId2, new SseEmitter(DEFAULT_TIMEOUT));
        String emitterId3 = memberId + "_" + System.currentTimeMillis();
        emitterRepository.save(emitterId3, new SseEmitter(DEFAULT_TIMEOUT));

        // When
        Map<String, SseEmitter> result = emitterRepository.findAllEmitterStartWithMemberId(memberId);

        // Then

        assertThat(result).hasSize(2);
        assertThat(result).containsKeys(emitterId1, emitterId3);
        assertThat(result).doesNotContainKeys(emitterId2);
    }



    @Test
    void findAllEventCacheStartWithByMemberIdTest() throws Exception {
        //given
        Long memberId = 1L;
        Member member = new Member(memberId, "test", "tester", new Location(37.5340, 126.7064), "abcd", "aaa@aa.com");

        String eventCacheId1 =  memberId + "_" + System.currentTimeMillis();
        BookProduct bookProduct1 = new BookProduct("수학의 정석1", "남궁성", "도우출판");
        Book book1 = Book.builder()
                .id(1L)
                .bookProduct(bookProduct1)
                .imgUrl("aaa@abc.com")
                .description("기본이 중요")
                .rentalFee(Money.of(1000))
                .location(new Location(37.5340, 126.7064))
                .member(member)
                .build();
        Alarm alarm1 = new Alarm(member, book1, AlarmType.MESSAGE, false);
        emitterRepository.saveEventCache(eventCacheId1, alarm1);

        Thread.sleep(100);
        String eventCacheId2 =  memberId + "_" + System.currentTimeMillis();
        BookProduct bookProduct2 = new BookProduct("수학의 정석2", "남궁성", "도우출판");
        Book book2 = Book.builder()
                .id(2L)
                .bookProduct(bookProduct2)
                .imgUrl("aaa@abc.com")
                .description("기본이 중요")
                .rentalFee(Money.of(1000))
                .location(new Location(37.5340, 126.7064))
                .member(member)
                .build();
        Alarm alarm2 = new Alarm(member, book2, AlarmType.MESSAGE, false);
        emitterRepository.saveEventCache(eventCacheId2, alarm2);

        Thread.sleep(100);
        String eventCacheId3 =  memberId + "_" + System.currentTimeMillis();
        BookProduct bookProduct3 = new BookProduct("수학의 정석3", "남궁성", "도우출판");
        Book book3 = Book.builder()
                .id(3L)
                .bookProduct(bookProduct3)
                .imgUrl("aaa@abc.com")
                .description("기본이 중요")
                .rentalFee(Money.of(1000))
                .location(new Location(37.5340, 126.7064))
                .member(member)
                .build();
        Alarm alarm3 = new Alarm(member, book3, AlarmType.MESSAGE, false);
        emitterRepository.saveEventCache(eventCacheId3, alarm3);

        //when
        Map<String, Object> ActualResult = emitterRepository.findAllEventCacheStartWithMemberId(memberId);

        //then
        assertEquals(3, ActualResult.size());
    }

    @Test
    void deleteByIdTest() throws Exception {
        //given
        Long memberId = 1L;
        String emitterId =  memberId + "_" + System.currentTimeMillis();
        SseEmitter sseEmitter = new SseEmitter(DEFAULT_TIMEOUT);

        //when
        emitterRepository.save(emitterId, sseEmitter);
        emitterRepository.deleteById(emitterId);

        //then
        assertEquals(0, emitterRepository.findAllEmitterStartWithMemberId(memberId).size());
    }

    @Test
    void deleteAllEmitterStartWithIdTest1() throws Exception {
        //given
        Long memberId = 1L;
        String emitterId1 = memberId + "_" + System.currentTimeMillis();
        emitterRepository.save(emitterId1, new SseEmitter(DEFAULT_TIMEOUT));

        Thread.sleep(100);
        String emitterId2 = memberId + "_" + System.currentTimeMillis();
        emitterRepository.save(emitterId2, new SseEmitter(DEFAULT_TIMEOUT));

        //when
        emitterRepository.deleteAllEmitterStartWithMemberId(memberId);

        //then
        assertEquals(0, emitterRepository.findAllEmitterStartWithMemberId(memberId).size());
    }

    @Test
    void deleteAllEmitterStartWithIdTest2() throws Exception {
        //given
        Long memberId = 1L;
        String emitterId1 = memberId + "_" + System.currentTimeMillis();
        emitterRepository.save(emitterId1, new SseEmitter(DEFAULT_TIMEOUT));

        Thread.sleep(100);
        String emitterId2 = memberId + "_" + System.currentTimeMillis();
        emitterRepository.save(emitterId2, new SseEmitter(DEFAULT_TIMEOUT));

        //when
        emitterRepository.deleteAllEmitterStartWithMemberId(null);

        //then
        assertEquals(2, emitterRepository.findAllEmitterStartWithMemberId(memberId).size());
    }

    @Test
    void deleteAllEventCacheStartWithIdTest1() throws Exception {
        //given
        Long memberId = 1L;
        Member member = new Member(memberId, "test", "tester", new Location(37.5340, 126.7064), "abcd", "aaa@aa.com");

        String eventCacheId1 =  memberId + "_" + System.currentTimeMillis();
        BookProduct bookProduct1 = new BookProduct("수학의 정석1", "남궁성", "도우출판");
        Book book1 = Book.builder()
                .id(1L)
                .bookProduct(bookProduct1)
                .imgUrl("aaa@abc.com")
                .description("기본이 중요")
                .rentalFee(Money.of(1000))
                .location(new Location(37.5340, 126.7064))
                .member(member)
                .build();
        Alarm alarm1 = new Alarm(member, book1, AlarmType.MESSAGE, false);
        emitterRepository.saveEventCache(eventCacheId1, alarm1);

        Thread.sleep(100);
        String eventCacheId2 =  memberId + "_" + System.currentTimeMillis();
        BookProduct bookProduct2 = new BookProduct("수학의 정석2", "남궁성", "도우출판");
        Book book2 = Book.builder()
                .id(2L)
                .bookProduct(bookProduct2)
                .imgUrl("aaa@abc.com")
                .description("기본이 중요")
                .rentalFee(Money.of(1000))
                .location(new Location(37.5340, 126.7064))
                .member(member)
                .build();
        Alarm alarm2 = new Alarm(member, book2, AlarmType.MESSAGE, false);
        emitterRepository.saveEventCache(eventCacheId2, alarm2);

        //when
        emitterRepository.deleteAllEventCacheStartWithMemberId(memberId);

        //then
        assertEquals(0, emitterRepository.findAllEventCacheStartWithMemberId(memberId).size());
    }

    @Test
    void deleteAllEventCacheStartWithIdTest2() throws Exception {
        //given
        Long memberId = 1L;
        Member member = new Member(memberId, "test", "tester", new Location(37.5340, 126.7064), "abcd", "aaa@aa.com");

        String eventCacheId1 =  memberId + "_" + System.currentTimeMillis();
        BookProduct bookProduct1 = new BookProduct("수학의 정석1", "남궁성", "도우출판");
        Book book1 = Book.builder()
                .id(1L)
                .bookProduct(bookProduct1)
                .imgUrl("aaa@abc.com")
                .description("기본이 중요")
                .rentalFee(Money.of(1000))
                .location(new Location(37.5340, 126.7064))
                .member(member)
                .build();
        Alarm alarm1 = new Alarm(member, book1, AlarmType.MESSAGE, false);
        emitterRepository.saveEventCache(eventCacheId1, alarm1);

        Thread.sleep(100);
        String eventCacheId2 =  memberId + "_" + System.currentTimeMillis();
        BookProduct bookProduct2 = new BookProduct("수학의 정석2", "남궁성", "도우출판");
        Book book2 = Book.builder()
                .id(2L)
                .bookProduct(bookProduct2)
                .imgUrl("aaa@abc.com")
                .description("기본이 중요")
                .rentalFee(Money.of(1000))
                .location(new Location(37.5340, 126.7064))
                .member(member)
                .build();
        Alarm alarm2 = new Alarm(member, book2, AlarmType.MESSAGE, false);
        emitterRepository.saveEventCache(eventCacheId2, alarm2);

        //when
        emitterRepository.deleteAllEventCacheStartWithMemberId(null);

        //then
        assertEquals(2, emitterRepository.findAllEventCacheStartWithMemberId(memberId).size());
    }
}
