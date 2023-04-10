package com.dongnebook.domain.chat.controller;

import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import com.dongnebook.domain.chat.domain.RedisChat;
import com.dongnebook.domain.chat.ui.RedisSubscriber;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class RedisSubscriberTest {

	@InjectMocks
	private RedisSubscriber redisSubscriber;

	@Mock
	private ObjectMapper objectMapper;

	@Mock
	private RedisTemplate<String, Object> redisTemplate;

	@Mock
	private SimpMessageSendingOperations messagingTemplate;


	@Test
	@DisplayName("레디스에서 받은 데이터를 역직렬화한다.")
	void testOnMessage() throws Exception {
		// Given
		Long roomId = 1L;
		Long senderId = 2L;
		String message = "hello";
		LocalDateTime createdAt = LocalDateTime.now();
		RedisChat redisChat = new RedisChat(roomId, senderId, message, createdAt);
		String publishMessage = "serialized redisChat";
		Message messageMock = mock(Message.class);

		when(messageMock.getBody()).thenReturn(publishMessage.getBytes());
		when(redisTemplate.getStringSerializer()).thenReturn(mock(StringRedisSerializer.class));
		when(redisTemplate.getStringSerializer().deserialize(any())).thenReturn(publishMessage);


		when(objectMapper.readValue(publishMessage, RedisChat.class)).thenReturn(redisChat);

		// When
		redisSubscriber.onMessage(messageMock, null);

		// Then
		verify(messagingTemplate, times(1)).convertAndSend("/sub/room/" + redisChat.getRoomId(), redisChat);
		verify(redisTemplate, times(2)).getStringSerializer();
		verify(objectMapper, times(1)).readValue(publishMessage, RedisChat.class);
		verifyNoMoreInteractions(messagingTemplate, redisTemplate, objectMapper);
	}
}