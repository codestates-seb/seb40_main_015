package com.dongnebook.domain.chat.ui;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import com.dongnebook.domain.chat.domain.ChatMessage;
import com.dongnebook.domain.chat.domain.RedisChat;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class RedisPublisher {
 private final RedisTemplate<String, Object> redisTemplate;

 public void publish(ChannelTopic topic, RedisChat message){
	 redisTemplate.convertAndSend(topic.getTopic(), message);
 }
}
