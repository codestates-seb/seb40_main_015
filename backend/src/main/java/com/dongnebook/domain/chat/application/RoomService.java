package com.dongnebook.domain.chat.application;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

import com.dongnebook.domain.book.application.BookService;
import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.chat.Repository.ChatRepository;
import com.dongnebook.domain.chat.Repository.RoomRepository;
import com.dongnebook.domain.chat.domain.ChatRoom;
import com.dongnebook.domain.chat.dto.request.RoomRequest;
import com.dongnebook.domain.chat.ui.RedisSubscriber;
import com.dongnebook.domain.member.application.MemberService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomService {
	private final ChatRepository chatRepository;
	private final RoomRepository roomRepository;
	private final MemberService memberService;
	private final BookService bookService;
	private final Map<String, ChannelTopic> topics;
	private final RedisMessageListenerContainer redisMessageListener;
	private final RedisSubscriber redisSubscriber;

	public Long getOrCreate(RoomRequest roomRequest) {

		Book book = bookService.getByBookId(roomRequest.getBookId());
		ChatRoom room = chatRepository.getOrCreate(roomRequest.getMerchantId(), roomRequest.getCustomerId())
			.orElseGet(() -> new ChatRoom(memberService.findById(roomRequest.getMerchantId()
			), memberService.findById(roomRequest.getCustomerId()), book));

		ChatRoom savedRoom = roomRepository.save(room);
		// Redis에 roomId란 이름의 새 토픽을 생성한다.
		String roomId = "room" + savedRoom.getId();
		if (topics.containsKey(roomId)) {
			ChannelTopic topic = new ChannelTopic(roomId);
			redisMessageListener.addMessageListener(redisSubscriber, topic);
			topics.put(roomId, topic);
		}

		return savedRoom.getId();
	}

	public List<ChatRoom> findAllMyRooms(Long memberId) {
		return roomRepository.findByMerchantIdOrCustomerId(memberId, memberId);
	}

	public ChatRoom findById(Long roomId) {
		return roomRepository.findById(roomId).orElseThrow(EntityNotFoundException::new);
	}

}
