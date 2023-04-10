package com.dongnebook.domain.chat.application;

import static com.dongnebook.support.BookStub.*;
import static com.dongnebook.support.MemberStub.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dongnebook.domain.alarm.application.AlarmService;
import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.chat.domain.ChatMessage;
import com.dongnebook.domain.chat.domain.ChatRoom;
import com.dongnebook.domain.chat.dto.request.ChatMessageDto;
import com.dongnebook.domain.chat.dto.response.ChatResponse;
import com.dongnebook.domain.chat.dto.response.ChatRoomResponse;
import com.dongnebook.domain.chat.dto.response.LastChatResponse;
import com.dongnebook.domain.chat.repository.ChatRepository;
import com.dongnebook.domain.member.application.MemberService;
import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.global.enums.AlarmType;

@ExtendWith(MockitoExtension.class)
class ChatServiceTest {
	@Mock
	private ChatRepository chatRepository;
	@Mock
	private RoomService roomService;
	@Mock
	private MemberService memberService;
	@Mock
	private AlarmService alarmService;

	@InjectMocks
	ChatService chatService;

	@Test
	@DisplayName("보낸 채팅을 저장한다.")
	void save() {
		Long memberAId = 1L;
		Long memberBId = 2L;
		Long roomId = 1L;
		Member memberA = MEMBER1.of(memberAId);
		Book memberABook = BOOK1.of();
		Member memberB = MEMBER2.of(memberBId);

		given(memberService.getById(memberAId)).willReturn(memberA);
		given(memberService.getById(memberBId)).willReturn(memberB);
		ChatRoom chatRoom = new ChatRoom(memberA, memberB, memberABook);
		given(roomService.findById(roomId)).willReturn(chatRoom);

		LocalDateTime now = LocalDateTime.now();
		ChatMessageDto testMessage = new ChatMessageDto(memberBId, memberAId, "테스트");
		ChatMessage 테스트 = ChatMessage.builder().receiver(memberA)
			.createdAt(now)
			.room(chatRoom)
			.sender(memberB)
			.content("테스트")
			.build();

		chatService.save(roomId, testMessage, now);

		then(chatRepository).should().save(eq(테스트));
		verify(alarmService).sendAlarm(memberA, memberABook, AlarmType.MESSAGE);
	}

	@Test
	@DisplayName("채팅방에 있는 모든 채팅 가져옴")
	void findAllChats(){
		Long memberAId = 1L;
		Long memberBId = 2L;
		Long roomId = 1L;
		Member memberA = MEMBER1.of(memberAId);
		Book memberABook = BOOK1.of();
		Member memberB = MEMBER2.of(memberBId);
		LocalDateTime now = LocalDateTime.now();
		ChatRoom chatRoom = new ChatRoom(memberA, memberB, memberABook);
		List<ChatMessage> messageList = List.of(ChatMessage.builder().receiver(memberA)
			.createdAt(now)
			.room(chatRoom)
			.sender(memberB)
			.content("테스트")
			.build());
		given(roomService.findById(roomId)).willReturn(chatRoom);
		given(chatRepository.findAllChatsInRoom(roomId)).willReturn(messageList);
		ChatRoomResponse chatRoomResponse = ChatRoomResponse.builder()
			.bookId(memberABook.getId())
			.bookUrl(memberABook.getImgUrl())
			.title(memberABook.getBookProduct().getTitle())
			.bookState(memberABook.getBookState())
			.chatResponses(messageList.stream().map(ChatResponse::of).collect(Collectors.toList()))
			.memberA(chatRoom.getCustomer())
			.memberB(chatRoom.getMerchant())
			.build();
		assertThat(chatService.findAllChats(roomId)).usingRecursiveComparison().isEqualTo(chatRoomResponse);
	}

	@Test
	@DisplayName("마지막 채팅들을 가져온다.")
	void findAllLastChats(){
		Long memberId = 1L;
		Long bookId = 1L;
		Member memberA = MEMBER1.of(memberId);
		Book bookA = BOOK1.of(bookId);
		Member memberB = MEMBER2.of(memberId);
		Member memberC = MEMBER3.of(memberId);
		given(memberService.getById(memberId)).willReturn(memberA);
		LocalDateTime now = LocalDateTime.now();
		ChatRoom chatRoomA = new ChatRoom(memberA, memberB,bookA);
		ChatRoom chatRoomB = new ChatRoom(memberA, memberC,bookA);
		List<ChatRoom> rooms = List.of(chatRoomA, chatRoomB);
		given(roomService.findAllMyRooms(memberId)).willReturn(rooms);
		ChatMessage message1 = ChatMessage.builder().receiver(memberA)
			.createdAt(now)
			.room(chatRoomA)
			.sender(memberB)
			.content("테스트")
			.build();

		ChatMessage message2 = ChatMessage.builder().receiver(memberA)
			.createdAt(now)
			.room(chatRoomA)
			.sender(memberC)
			.content("테스트")
			.build();

		LastChatResponse lastChat1 = LastChatResponse.of(memberB, message1);
		LastChatResponse lastChat2 = LastChatResponse.of(memberC, message2);

		given(chatRepository.findAllLastChats(rooms)).willReturn(List.of(message1,message2));

		List<LastChatResponse> allLastChats = chatService.findAllLastChats(memberId);
		Assertions.assertThat(allLastChats).hasSize(2);
		Assertions.assertThat(allLastChats.get(0).getLatestMessage()).isEqualTo(lastChat1.getLatestMessage());

	}

}

