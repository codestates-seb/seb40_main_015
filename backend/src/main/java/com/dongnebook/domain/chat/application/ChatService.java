package com.dongnebook.domain.chat.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dongnebook.domain.chat.Repository.ChatRepository;
import com.dongnebook.domain.chat.domain.ChatMessage;
import com.dongnebook.domain.chat.domain.ChatRoom;
import com.dongnebook.domain.chat.dto.request.ChatMessageDto;
import com.dongnebook.domain.chat.dto.response.ChatResponse;
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

	//채팅방에 있는 모든 채팅 가져옴
	public List<ChatResponse> findAllChats(Long roomId) {
		List<ChatMessage> chats = chatRepository.findAllChatsInRoom(roomId);
		return chats.stream()
			.map(ChatResponse::of)
			.collect(Collectors.toList());
	}

	public List<LastChatResponse> findAllLastChats(Long memberId) {
		Member member = memberService.findById(memberId);
		List<ChatRoom> rooms = roomService.findAllMyRooms(member.getId());
		List<ChatMessage> allLastChats = chatRepository.findAllLastChats(rooms);
		return allLastChats.stream().map(
			chat -> LastChatResponse.of(chat.getRoom().getPartner(memberId), chat)).collect(Collectors.toList());
	}

	@Transactional
	public void save(Long roomId, ChatMessageDto message) {
		Member sender = memberService.findById(message.getSenderId());
		Member receiver = memberService.findById(message.getReceiverId());
		ChatRoom room = roomService.findById(roomId);
		ChatMessage chat = new ChatMessage(sender, receiver, message.getContent(), room);
		chatRepository.save(chat);
	}

}
