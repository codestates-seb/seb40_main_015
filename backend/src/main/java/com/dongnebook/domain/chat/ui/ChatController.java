package com.dongnebook.domain.chat.ui;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.dongnebook.domain.chat.dto.ChatMessageDto;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatController {
	// private final SimpMessagingTemplate template;
	//
	// @MessageMapping("/chat/enter")
	// public void enter(ChatMessageDto message){
	// 	message.setContent(message.getSender()+"ㅎㅇ");
	// 	template.convertAndSend("/sub/chat/room/" + message.getRoomId(),message);
	// }
	//
	// @MessageMapping("/chat/message")
	// public void message(ChatMessageDto message){
	// 	template.convertAndSend("/sub/chat/room/"+message.getRoomId(),message);
	//
	// }

}
