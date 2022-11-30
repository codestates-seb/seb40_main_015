package com.dongnebook.domain.chat.ui;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import com.dongnebook.domain.chat.domain.ChatMessage;
import com.dongnebook.domain.chat.domain.RedisChat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisSubscriber implements MessageListener {

	private final ObjectMapper objectMapper;
	private final RedisTemplate redisTemplate;
	private final SimpMessageSendingOperations messagingTemplate;

	@Override
	public void onMessage(Message message, byte[] pattern) {
		try {
			//레디스 에서 받은 데이터를 역직렬화
			String publishMessage = String.valueOf(redisTemplate.getStringSerializer().deserialize(message.getBody()));
			RedisChat redisChat = objectMapper.readValue(publishMessage, RedisChat.class);
			messagingTemplate.convertAndSend("/sub/room/" + redisChat.getRoomId(), redisChat.getMessage());
			log.info("레디스에서 받아옴");
		}
		catch (Exception e){
			log.error(e.getMessage());
		}
	}
}