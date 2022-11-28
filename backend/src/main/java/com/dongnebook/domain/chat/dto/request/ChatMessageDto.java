package com.dongnebook.domain.chat.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatMessageDto {
@NotNull
private Long senderId;
@NotNull
private Long receiverId;
@NotBlank
private String content;

	public ChatMessageDto(Long senderId, Long receiverId, String content) {
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.content = content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
