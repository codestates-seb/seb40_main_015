package com.dongnebook.domain.chat.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dongnebook.domain.chat.domain.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage,Long> {
}
