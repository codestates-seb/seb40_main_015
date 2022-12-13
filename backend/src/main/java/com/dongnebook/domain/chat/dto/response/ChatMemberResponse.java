package com.dongnebook.domain.chat.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatMemberResponse {

	private Long memberId;
	private String avatarUrl;
	private String nickName;

	@Builder
	public ChatMemberResponse(Long memberId, String avatarUrl, String nickName) {
		this.memberId = memberId;
		this.avatarUrl = avatarUrl;
		this.nickName = nickName;
	}
}
