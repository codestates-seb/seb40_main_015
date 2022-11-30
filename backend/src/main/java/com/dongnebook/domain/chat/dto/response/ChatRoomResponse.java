package com.dongnebook.domain.chat.dto.response;

import java.util.List;

import com.dongnebook.domain.book.domain.BookState;
import com.dongnebook.domain.member.domain.Member;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatRoomResponse {

	private Long bookId;

	private String title;

	private BookState bookState;

	private String bookUrl;

	private List<ChatMemberResponse> members;

	List<ChatResponse> chatResponses;

	@Builder
	public ChatRoomResponse(Long bookId, String title, BookState bookState, String bookUrl,
		Member memberA, Member memberB, List<ChatResponse> chatResponses) {
		this.bookId = bookId;
		this.title = title;
		this.bookState = bookState;
		this.bookUrl = bookUrl;
		this.members = List.of(
			ChatMemberResponse.builder().memberId(memberA.getId()).avatarUrl(memberA.getAvatarUrl())
			.nickName(memberA.getNickname()).build(),
			ChatMemberResponse.builder().memberId(memberB.getId()).avatarUrl(memberB.getAvatarUrl()).nickName(
			memberB.getNickname()).build());
		this.chatResponses = chatResponses;
	}

}
