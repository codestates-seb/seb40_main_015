package com.dongnebook.domain.chat.dto.response;

import java.time.LocalDateTime;

import com.dongnebook.domain.chat.domain.ChatMessage;
import com.dongnebook.domain.member.domain.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LastChatResponse {

	private Long roomId;

	private Long receiverId;

	private String name;

	private String imageUrl;

	private String latestMessage;

	private LocalDateTime createdAt;

	@Builder
	public LastChatResponse(Long roomId, Long receiverId, String name, String imageUrl, String latestMessage,
		LocalDateTime createdAt) {
		this.roomId = roomId;
		this.receiverId = receiverId;
		this.name = name;
		this.imageUrl = imageUrl;
		this.latestMessage = latestMessage;
		this.createdAt = createdAt;
	}

	public static LastChatResponse of(Member partner, ChatMessage latestChat) {
		return LastChatResponse.builder()
			.roomId(latestChat.getRoom().getId())
			.receiverId(partner.getId())
			.name(partner.getNickname())
			.imageUrl(partner.getAvatarUrl())
			.latestMessage(latestChat.getContent())
			.createdAt(latestChat.getCreatedAt())
			.build();
	}
}