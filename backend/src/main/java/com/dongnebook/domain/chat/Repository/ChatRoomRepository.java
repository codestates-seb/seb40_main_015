package com.dongnebook.domain.chat.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dongnebook.domain.chat.domain.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>{

}
