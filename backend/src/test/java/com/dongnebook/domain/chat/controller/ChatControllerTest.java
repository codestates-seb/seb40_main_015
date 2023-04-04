package com.dongnebook.domain.chat.controller;

import static com.dongnebook.support.MemberStub.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.chat.application.ChatService;
import com.dongnebook.domain.chat.application.RoomService;
import com.dongnebook.domain.chat.domain.ChatMessage;
import com.dongnebook.domain.chat.domain.ChatRoom;
import com.dongnebook.domain.chat.domain.RedisChat;
import com.dongnebook.domain.chat.dto.request.ChatMessageDto;
import com.dongnebook.domain.chat.dto.request.RoomRequest;
import com.dongnebook.domain.chat.dto.response.ChatResponse;
import com.dongnebook.domain.chat.dto.response.ChatRoomResponse;
import com.dongnebook.domain.chat.dto.response.LastChatResponse;
import com.dongnebook.domain.chat.ui.ChatController;
import com.dongnebook.domain.chat.ui.RedisPublisher;
import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.support.BookStub;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest({ChatController.class})
@MockBean(JpaMetamodelMappingContext.class)
class ChatControllerTest {
	@Autowired
	MockMvc mockMvc;

	@MockBean
	private RedisPublisher redisPublisher;

	@MockBean
	ChatService chatService;

	@MockBean
	RoomService roomService;

	@WithMockUser
	@DisplayName("채팅을 보낸다")
	void chat() throws Exception {
		Long roomId = 1L;
		Long senderId = 1L;
		Long receiverId = 2L;
		ChatMessageDto message = new ChatMessageDto(senderId, receiverId, "테스트");
		doNothing().when(redisPublisher).publish(any(ChannelTopic.class), any(RedisChat.class));
		doNothing().when(chatService).save(eq(roomId), eq(message), any(LocalDateTime.class));
		mockMvc.perform(
				post("/chats/message/{roomId}", roomId)
					.contentType(MediaType.APPLICATION_JSON)
					.content(new ObjectMapper().writeValueAsString(message))
					.with(csrf()))

			.andExpect(status().isOk());

		verify(redisPublisher, times(1)).publish(any(ChannelTopic.class), any(RedisChat.class));
		verify(chatService, times(1)).save(eq(roomId), eq(message), any(LocalDateTime.class));
	}

	@Test
	@WithMockUser
	@DisplayName("채팅방의 모든 채팅을 찾는다.")
	void testFindAllChats() throws Exception {
		Member memberA = MEMBER1.of(1L);
		Member memberB = MEMBER2.of(2L);
		Book bookA = BookStub.BOOK1.of(1L);
		ChatRoom chatRoom = new ChatRoom(memberA, memberB, bookA);
		Long roomId = chatRoom.getId();
		List<ChatResponse> chats = new ArrayList<>();
		ChatMessage chat = ChatMessage.builder().receiver(memberA)
			.room(chatRoom)
			.createdAt(LocalDateTime.now())
			.sender(memberB)
			.content("content").build();
		chats.add(ChatResponse.of(chat));

		ChatRoomResponse response = ChatRoomResponse.builder()
			.bookState(bookA.getBookState())
			.chatResponses(List.of(ChatResponse.of(chat)))
			.bookId(bookA.getId())
			.title(bookA.getBookProduct().getTitle())
			.bookUrl(bookA.getImgUrl())
			.memberA(memberA)
			.memberB(memberB)
			.build();

		when(chatService.findAllChats(any())).thenReturn(response);

		// Perform the request
		mockMvc.perform(get("/chats/message/{roomId}", 1L)
			.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.bookId").value(1))
			.andExpect(jsonPath("$.title").value("자바의 정석"))
			.andExpect(jsonPath("$.bookState").value("RENTABLE"))
			.andExpect(jsonPath("$.bookUrl").isNotEmpty())
			.andExpect(jsonPath("$.members").isArray())
			.andExpect(jsonPath("$.members[0].memberId").value(1))
			.andExpect(jsonPath("$.members[0].avatarUrl").value("www.naver.com"))
			.andExpect(jsonPath("$.members[0].nickName").value("이성준"))
			.andExpect(jsonPath("$.members[1].memberId").value(2))
			.andExpect(jsonPath("$.members[1].avatarUrl").value("www.naver.com"))
			.andExpect(jsonPath("$.members[1].nickName").value("이성준2"))
			.andExpect(jsonPath("$.chatResponses").isArray())
			.andExpect(jsonPath("$.chatResponses[0].memberId").value(2))
			.andExpect(jsonPath("$.chatResponses[0].avatarUrl").value("www.naver.com"))
			.andExpect(jsonPath("$.chatResponses[0].nickName").value("이성준2"))
			.andExpect(jsonPath("$.chatResponses[0].content").value("content"));

		// Verify the mocks
		verify(chatService, times(1)).findAllChats(any());
	}

	@Test
	@WithMockUser
	@DisplayName("채팅창에 있는 모든 마지막 대화를 가져온다.")
	void findAllLastChats() throws Exception {
		Member memberA = MEMBER1.of(1L);
		Member memberB = MEMBER2.of(2L);
		Book bookA = BookStub.BOOK1.of(1L);
		ChatRoom chatRoom = new ChatRoom(memberA, memberB, bookA);
		List<ChatResponse> chats = new ArrayList<>();
		ChatMessage chat = ChatMessage.builder().receiver(memberA)
			.room(chatRoom)
			.createdAt(LocalDateTime.now())
			.sender(memberB)
			.content("content").build();
		chats.add(ChatResponse.of(chat));

		ChatRoomResponse response = ChatRoomResponse.builder()
			.bookState(bookA.getBookState())
			.chatResponses(List.of(ChatResponse.of(chat)))
			.bookId(bookA.getId())
			.title(bookA.getBookProduct().getTitle())
			.bookUrl(bookA.getImgUrl())
			.memberA(memberA)
			.memberB(memberB)
			.build();
		LastChatResponse lastChatResponse = LastChatResponse.of(memberB, chat);
		when(chatService.findAllLastChats(any())).thenReturn(List.of(lastChatResponse));

		// Perform the request
		mockMvc.perform(get("/rooms")
				.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].merchantId").value(1))
			.andExpect(jsonPath("$[0].customerId").value(2))
			.andExpect(jsonPath("$[0].bookId").value(1))
			.andExpect(jsonPath("$[0].name").value("이성준2"))
			.andExpect(jsonPath("$[0].latestMessage").value("content"))
			.andExpect(jsonPath("$[0].avatarUrl").value("www.naver.com"))
			.andExpect(jsonPath("$[0].bookImageUrl").value("이미지 주소"));
	}

	@Test
	@DisplayName("채팅방이 있으면 기존것을 쓰고 없으면 새로 만든다.")
	@WithMockUser
	void createOrGet() throws Exception {
		Member memberA = MEMBER1.of(1L);
		Member memberB = MEMBER2.of(2L);
		Book bookA = BookStub.BOOK1.of(1L);
		new RoomRequest(memberA.getId(),memberB.getId(),bookA.getId());
		mockMvc.perform(get("/room")
				.param("merchantId", memberA.getId().toString())
				.param("customerId", memberB.getId().toString())
				.param("bookId", bookA.getId().toString())
				.with(csrf()))
			.andExpect(status().isOk());
	}
}