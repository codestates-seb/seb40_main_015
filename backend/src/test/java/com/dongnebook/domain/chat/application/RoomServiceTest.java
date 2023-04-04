package com.dongnebook.domain.chat.application;

import static com.dongnebook.support.BookStub.*;
import static com.dongnebook.support.MemberStub.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import com.dongnebook.domain.book.application.BookQueryService;
import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.chat.domain.ChatRoom;
import com.dongnebook.domain.chat.dto.request.RoomRequest;
import com.dongnebook.domain.chat.repository.ChatRepository;
import com.dongnebook.domain.chat.repository.RoomRepository;
import com.dongnebook.domain.chat.ui.RedisSubscriber;
import com.dongnebook.domain.member.application.MemberService;
import com.dongnebook.domain.member.domain.Member;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {
	@Mock
	ChatRepository chatRepository;
	@Mock
	RoomRepository roomRepository;
	@Mock
	MemberService memberService;
	@Mock
	BookQueryService bookQueryService;
	@Mock
	Map<String, ChannelTopic> topics;
	@Mock
	RedisMessageListenerContainer redisMessageListener;
	@Mock
	RedisSubscriber redisSubscriber;
	@InjectMocks
	RoomService roomService;

	@Test
	@DisplayName("roomId로 채팅방을 찾는다.")
	void findById() {
		Long roomId = 1L;
		Member memberA = MEMBER1.of(1L);
		Book bookA = BOOK1.of();
		Member memberB = MEMBER2.of(2L);
		ChatRoom chatRoom = new ChatRoom(memberA, memberB, bookA);
		given(roomRepository.findById(roomId)).willReturn(Optional.of(chatRoom));
		assertThat(roomService.findById(roomId).getMerchant().getId()).isEqualTo(memberA.getId());
		assertThat(roomService.findById(roomId).getCustomer().getId()).isEqualTo(memberB.getId());
	}

	@Test
	@DisplayName("내 채팅방 목록을 가져온다.")
	void findAllMyRooms() {
		Long roomId = 1L;
		Long memberAId = 1L;
		Long bookId = 1L;
		Long memberBId = 2L;
		Member memberB = MEMBER2.of(memberBId);
		Member memberA = MEMBER1.of(memberAId);
		Book bookA = BOOK1.of(bookId);
		ChatRoom chatRoom = new ChatRoom(memberA, memberB, bookA);
		List<ChatRoom> rooms = List.of(chatRoom);
		given(roomRepository.findByMerchantIdOrCustomerId(1L, 1L)).willReturn(rooms);
		assertThat(roomService.findAllMyRooms(1L).get(0)).usingRecursiveComparison().isEqualTo(chatRoom);
	}

	@Test
	@DisplayName("채팅방이 있으면 있는 채팅방을 반환하고 없으면 새로 채팅방을 만든다.(채팅방이 없을때)")
	void findOrCreate() {
		Long roomId = 1L;
		Long memberAId = 1L;
		Long bookId = 1L;
		Long memberBId = 2L;
		Member memberB = MEMBER2.of(memberBId);
		Member memberA = MEMBER1.of(memberAId);
		Book bookA = BOOK1.of(bookId);
		RoomRequest roomRequest = new RoomRequest(memberAId, memberBId, bookId);
		ChatRoom chatRoom = new ChatRoom(roomId, memberA, memberB, bookA, LocalDateTime.now());
		given(roomRepository.save(any())).willReturn(chatRoom);
		given(bookQueryService.getByBookId(bookId)).willReturn(bookA);
		given(chatRepository.findOrCreate(memberAId, memberBId, bookId)).willReturn(Optional.empty());
		Long orCreate = roomService.findOrCreate(roomRequest);

		verify(topics).computeIfAbsent(anyString(), any());
		assertEquals(chatRoom.getId(), orCreate);
	}

	@Test
	@DisplayName("채팅방이 있으면 있는 채팅방을 반환하고 없으면 새로 채팅방을 만든다.(채팅방이 있을때)")
	void findOrCreate2() {
		Long roomId = 1L;
		Long memberAId = 1L;
		Long bookId = 1L;
		Long memberBId = 2L;
		Member memberB = MEMBER2.of(memberBId);
		Member memberA = MEMBER1.of(memberAId);
		Book bookA = BOOK1.of(bookId);
		RoomRequest roomRequest = new RoomRequest(memberAId, memberBId, bookId);
		ChatRoom chatRoom = new ChatRoom(roomId, memberA, memberB, bookA, LocalDateTime.now());
		given(roomRepository.save(chatRoom)).willReturn(chatRoom);
		given(bookQueryService.getByBookId(bookId)).willReturn(bookA);
		given(chatRepository.findOrCreate(memberAId, memberBId, bookId)).willReturn(Optional.of(chatRoom));
		Long orCreate = roomService.findOrCreate(roomRequest);

		verify(topics).computeIfAbsent(anyString(), any());
		assertEquals(chatRoom.getId(), orCreate);
	}
}
