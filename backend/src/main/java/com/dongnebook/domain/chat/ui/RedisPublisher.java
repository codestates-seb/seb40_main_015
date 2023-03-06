package com.dongnebook.domain.chat.ui;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import com.dongnebook.domain.chat.domain.RedisChat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class RedisPublisher {
 private final RedisTemplate<String, Object> redisTemplate;

 public void publish(ChannelTopic topic, RedisChat message){
	 redisTemplate.convertAndSend(topic.getTopic(), message);
	 log.info("레디스로 보냄");
 }
}
