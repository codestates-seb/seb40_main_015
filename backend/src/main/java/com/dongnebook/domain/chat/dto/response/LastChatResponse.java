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

	private String latestMessage;

	private LocalDateTime createdAt;

	private String avatarUrl;

	private String bookImageUrl;

	@Builder
	public LastChatResponse(Long roomId, Long receiverId, String name, String avatarUrl, String latestMessage,
		LocalDateTime createdAt, String bookImageUrl) {
		this.roomId = roomId;
		this.receiverId = receiverId;
		this.name = name;
		this.avatarUrl = avatarUrl;
		this.latestMessage = latestMessage;
		this.createdAt = createdAt;
		this.bookImageUrl = bookImageUrl;
	}

	public static LastChatResponse of(Member partner, ChatMessage latestChat) {
		return LastChatResponse.builder()
			.roomId(latestChat.getRoom().getId())
			.receiverId(partner.getId())
			.name(partner.getNickname())
			.avatarUrl(partner.getAvatarUrl())
			.bookImageUrl(latestChat.getRoom().getBook().getImgUrl())
			.latestMessage(latestChat.getContent())
			.createdAt(latestChat.getCreatedAt())
			.build();
	}
}