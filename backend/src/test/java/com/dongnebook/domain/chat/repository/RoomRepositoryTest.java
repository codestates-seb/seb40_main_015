package com.dongnebook.domain.chat.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

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
public class RoomRepositoryTest {
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
	@DisplayName("상인id나 손님id로 채팅방을 찾는다.")
	void findByMerchantIdOrCustomerId(){
		List<ChatRoom> chatRooms = roomRepository.findByMerchantIdOrCustomerId(memberA.getId(),
			memberB.getId());
		assertThat(chatRooms).size().isEqualTo(1);


	}

}
