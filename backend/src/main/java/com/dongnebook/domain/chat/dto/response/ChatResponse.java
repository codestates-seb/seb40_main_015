package com.dongnebook.domain.chat.dto.response;

import java.time.LocalDateTime;

import com.dongnebook.domain.chat.domain.ChatMessage;

import lombok.Getter;

@Getter
public class ChatResponse {
	private Long memberId;
	private String avatarUrl;
	private String nickName;
	private String content;
	private LocalDateTime dateTime;


	public ChatResponse(Long memberId, String avatarUrl, String nickName, String content, LocalDateTime dateTime) {
		this.memberId = memberId;
		this.avatarUrl = avatarUrl;
		this.nickName = nickName;
		this.content = content;
		this.dateTime = dateTime;
	}

	public static ChatResponse of(ChatMessage chatMessage){
		return new ChatResponse(chatMessage.getSender().getId(), chatMessage.getSender().getAvatarUrl(), chatMessage.getSender().getNickname(), chatMessage.getContent(), chatMessage.getCreatedAt());
	}


}
