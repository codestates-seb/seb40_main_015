package com.dongnebook.domain.chat.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.dongnebook.config.TestConfig;
import com.dongnebook.domain.book.adapter.out.BookCommandRepository;
import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.chat.domain.ChatMessage;
import com.dongnebook.domain.chat.domain.ChatRoom;
import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.member.repository.MemberRepository;
import com.dongnebook.support.BookStub;
import com.dongnebook.support.DataClearExtension;
import com.dongnebook.support.DatabaseCleaner;
import com.dongnebook.support.MemberStub;
import com.github.gavlyukovskiy.boot.jdbc.decorator.DataSourceDecoratorAutoConfiguration;

@DataJpaTest(showSql = false)
@ImportAutoConfiguration(DataSourceDecoratorAutoConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({TestConfig.class, DatabaseCleaner.class})
@ExtendWith(DataClearExtension.class)
class ChatRepositoryTest {

	@Autowired
	ChatRepository chatRepository;
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	RoomRepository roomRepository;
	@Autowired
	BookCommandRepository bookCommandRepository;

	private Member memberA;
	private Member memberB;
	private Book bookA;
	private ChatRoom chatRoom;
	private ChatMessage message;

	@BeforeEach
	void setUp(){
		memberA = MemberStub.MEMBER1.of();
		memberRepository.save(memberA);
		memberB = MemberStub.MEMBER2.of();
		memberRepository.save(memberB);
		bookA = BookStub.BOOK1.of();
		bookCommandRepository.save(bookA);
		chatRoom = new ChatRoom(memberA, memberB, bookA);
		roomRepository.save(chatRoom);
		message = ChatMessage.builder()
			.content("테스트")
			.sender(memberB)
			.room(chatRoom)
			.createdAt(LocalDateTime.now())
			.receiver(memberA)
			.build();
		chatRepository.save(message);
	}

	@Test
	@DisplayName("모든 마지막 대화를 가져온다.")
	void findAllLastChats(){
		List<ChatMessage> allLastChats = chatRepository.findAllLastChats(List.of(chatRoom));
		assertEquals(allLastChats.get(0).getContent(), message.getContent());
		assertEquals(allLastChats.get(0).getRoom().getId(), message.getRoom().getId());
		assertEquals(allLastChats.get(0).getReceiver(), message.getReceiver());
		assertEquals(allLastChats.get(0).getSender(), message.getSender());
	}

	@Test
	@DisplayName("메시지 하나를 저장한다.")
	void save(){
		message = ChatMessage.builder()
			.content("테스트2")
			.sender(memberB)
			.room(chatRoom)
			.createdAt(LocalDateTime.now())
			.receiver(memberA)
			.build();
		chatRepository.save(message);
		assertThat(message.getId()).isNotNull();

	}

	@Test
	@DisplayName("채팅방의 모든 대화를 가져온다.")
	void findAllChatsInRoom(){
		Long roomId = chatRoom.getId();
		List<ChatMessage> allChatsInRoom = chatRepository.findAllChatsInRoom(roomId);
		ChatMessage chatMessage = allChatsInRoom.get(0);
		assertEquals(chatMessage.getContent(), message.getContent());
		assertEquals(chatMessage.getId(), message.getRoom().getId());
		assertEquals(chatMessage.getReceiver(), message.getReceiver());
		assertEquals(chatMessage.getSender(), message.getSender());
	}

	@Test
	@DisplayName("채팅방 하나를 찾는다.")
	void findOrCreate(){
		Long merchantId = memberA.getId();
		Long customerId = memberB.getId();
		Long bookId = bookA.getId();

		Optional<ChatRoom> orCreate = chatRepository.findOrCreate(merchantId, customerId, bookId);
		assertThat(orCreate.get().getMerchant()).isEqualTo(memberA);
		assertThat(orCreate.get().getCustomer()).isEqualTo(memberB);
		assertThat(orCreate.get().getBook()).isEqualTo(bookA);
	}
}
