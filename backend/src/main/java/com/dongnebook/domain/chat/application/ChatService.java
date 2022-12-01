package com.dongnebook.domain.chat.application;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dongnebook.domain.alarm.domain.AlarmService;
import com.dongnebook.domain.alarm.domain.AlarmType;
import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.chat.Repository.ChatRepository;
import com.dongnebook.domain.chat.domain.ChatMessage;
import com.dongnebook.domain.chat.domain.ChatRoom;
import com.dongnebook.domain.chat.dto.request.ChatMessageDto;
import com.dongnebook.domain.chat.dto.response.ChatResponse;
import com.dongnebook.domain.chat.dto.response.ChatRoomResponse;
import com.dongnebook.domain.chat.dto.response.LastChatResponse;
import com.dongnebook.domain.member.application.MemberService;
import com.dongnebook.domain.member.domain.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatService {
	private final ChatRepository chatRepository;
	private final RoomService roomService;
	private final MemberService memberService;
	private final AlarmService alarmService;

	//채팅방에 있는 모든 채팅 가져옴
	public ChatRoomResponse findAllChats(Long roomId) {
		ChatRoom chatRoom = roomService.findById(roomId);
		Book book = chatRoom.getBook();

		List<ChatMessage> chats = chatRepository.findAllChatsInRoom(roomId);

		List<ChatResponse> collect = chats.stream()
			.map(ChatResponse::of)
			.collect(Collectors.toList());
		return ChatRoomResponse.builder().bookId(book.getId())
			.bookUrl(book.getImgUrl())
			.title(book.getTitle())
			.bookState(book.getBookState())
			.chatResponses(collect)
			.memberA(chatRoom.getCustomer())
			.memberB(chatRoom.getMerchant())
			.build();
	}

	public List<LastChatResponse> findAllLastChats(Long memberId) {
		Member member = memberService.findById(memberId);
		List<ChatRoom> rooms = roomService.findAllMyRooms(member.getId());
		List<ChatMessage> allLastChats = chatRepository.findAllLastChats(rooms);
		return allLastChats.stream().map(
			chat -> LastChatResponse.of(chat.getRoom().getPartner(memberId), chat)).collect(Collectors.toList());
	}

	@Transactional
	public void save(Long roomId, ChatMessageDto message,LocalDateTime now) {
		Member sender = memberService.findById(message.getSenderId());
		Member receiver = memberService.findById(message.getReceiverId());
		ChatRoom room = roomService.findById(roomId);
		ChatMessage chat = new ChatMessage(sender, receiver, message.getContent(), room, now);
		chatRepository.save(chat);
		alarmService.sendAlarm(receiver,room.getBook(), AlarmType.MESSAGE);
	}

}
