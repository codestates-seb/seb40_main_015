package com.dongnebook.domain.chat.ui;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.dongnebook.domain.chat.application.ChatService;
import com.dongnebook.domain.chat.application.RoomService;
import com.dongnebook.domain.chat.domain.RedisChat;
import com.dongnebook.domain.chat.dto.request.ChatMessageDto;
import com.dongnebook.domain.chat.dto.request.RoomRequest;
import com.dongnebook.domain.chat.dto.response.LastChatResponse;
import com.dongnebook.global.security.auth.annotation.Login;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ChatController {
	private final RedisPublisher redisPublisher;
	private final ChatService chatService;
	private final RoomService roomService;

	//클라이언트가 send를 할수 있는 경로
	@MessageMapping("/chats/message/{roomId}")
	public void chat(@DestinationVariable Long roomId, ChatMessageDto message) {

		LocalDateTime now = LocalDateTime.now();

		redisPublisher.publish(ChannelTopic.of("room"+roomId), new RedisChat(roomId,message.getSenderId(),
			message.getContent(), LocalDateTime.now()));
		chatService.save(roomId,message,now);
	}

	//채팅방에 있는 대화를 모두 가져옴
	@GetMapping("/chats/message/{roomId}")
	public ResponseEntity<Object> findAllChats(@PathVariable Long roomId){
		return ResponseEntity.ok(chatService.findAllChats(roomId));
	}

	@GetMapping("/rooms")
	public List<LastChatResponse> findAllLastChats(@Login Long memberId){
		return chatService.findAllLastChats(memberId);
	}

	@GetMapping("/room")
	public ResponseEntity<Long> createOrGet(@ModelAttribute @Valid RoomRequest roomRequest) {

		return ResponseEntity.ok(roomService.findOrCreate(roomRequest));
	}
}
