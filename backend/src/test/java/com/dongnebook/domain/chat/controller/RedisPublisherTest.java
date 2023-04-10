package com.dongnebook.domain.chat.controller;

import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;

import com.dongnebook.domain.chat.domain.RedisChat;
import com.dongnebook.domain.chat.ui.RedisPublisher;

@ExtendWith(MockitoExtension.class)
class RedisPublisherTest {
	@InjectMocks
	private RedisPublisher redisPublisher;

	@Mock
	private RedisTemplate<String, Object> redisTemplate;

	@Test
	@DisplayName("레디스로 채팅을 보낸다.")
	void publish(){
		// Given
		Long roomId = 1L;
		Long senderId = 2L;
		String message = "hello";
		LocalDateTime createdAt = LocalDateTime.now();
		RedisChat redisChat = new RedisChat(roomId, senderId, message, createdAt);
		doNothing().when(redisTemplate).convertAndSend(anyString(), eq(redisChat));
		ChannelTopic topic = ChannelTopic.of("room" + roomId);

		// When
		redisPublisher.publish(topic,redisChat);

		// Then
		verify(redisTemplate,times(1)).convertAndSend(String.valueOf(topic),redisChat);
	}
}