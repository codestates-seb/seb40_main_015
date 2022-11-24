package com.dongnebook.domain.chat.dto;

import lombok.Getter;

@Getter
public class ChatMessageDto {
private Long roomId;
private String sender;
private String content;

	public ChatMessageDto(String sender, String content) {
		this.sender = sender;
		this.content = content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
