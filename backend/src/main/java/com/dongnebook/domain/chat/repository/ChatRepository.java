package com.dongnebook.domain.chat.repository;

import static com.dongnebook.domain.chat.domain.QChatMessage.*;
import static com.dongnebook.domain.chat.domain.QChatRoom.*;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.dongnebook.domain.chat.domain.ChatMessage;
import com.dongnebook.domain.chat.domain.ChatRoom;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ChatRepository {

	private final JPAQueryFactory jpaQueryFactory;
	private final EntityManager em;

	public List<ChatMessage> findAllLastChats(List<ChatRoom> rooms) {
		return jpaQueryFactory
			.selectFrom(chatMessage)
			.where(Expressions.list(chatMessage.room, chatMessage.createdAt)
				.in((JPAExpressions
					.select(chatMessage.room, chatMessage.createdAt.max())
					.from(chatMessage)
					.where(chatMessage.room.in(rooms))
					.groupBy(chatMessage.room))
				))
			.orderBy(chatMessage.createdAt.desc())
			.fetch();
	}

	public void save(ChatMessage message) {
		em.persist(message);
	}

	public List<ChatMessage> findAllChatsInRoom(Long roomId) {
		return jpaQueryFactory.select(chatMessage)
			.from(chatMessage)
			.where(chatMessage.room.id.eq(roomId))
			.fetch();
	}

	public Optional<ChatRoom> findOrCreate(Long merchantId, Long customerId, Long bookId) {
		return Optional.ofNullable(jpaQueryFactory
			.selectFrom(chatRoom)
			.where(chatRoom.customer.id.eq(customerId).and(chatRoom.merchant.id.eq(merchantId))
				.and(chatRoom.book.id.eq(bookId)))
			.fetchFirst());

	}

}


