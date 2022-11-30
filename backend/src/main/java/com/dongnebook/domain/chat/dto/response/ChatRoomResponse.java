package com.dongnebook.domain.chat.dto.response;

import java.util.List;

import com.dongnebook.domain.book.domain.BookState;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatRoomResponse {

	private Long bookId;

	private String title;

	private BookState bookState;

	private String bookUrl;

	private Long member1Id;
	private Long member2Id;

	List<ChatResponse> chatResponses;

	@Builder
	public ChatRoomResponse(Long bookId, String title, BookState bookState, String bookUrl,
		Long member1Id, Long member2Id, List<ChatResponse> chatResponses) {
		this.bookId = bookId;
		this.title = title;
		this.bookState = bookState;
		this.bookUrl = bookUrl;
		this.member1Id = member1Id;
		this.member2Id = member2Id;

		this.chatResponses = chatResponses;
	}


}
