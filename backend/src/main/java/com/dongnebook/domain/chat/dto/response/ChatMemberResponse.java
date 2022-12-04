package com.dongnebook.domain.chat.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatMemberResponse {

	public Long memberId;
	public String avatarUrl;
	public String nickName;

	@Builder
	public ChatMemberResponse(Long memberId, String avatarUrl, String nickName) {
		this.memberId = memberId;
		this.avatarUrl = avatarUrl;
		this.nickName = nickName;
	}
}
