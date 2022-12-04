package com.dongnebook.domain.chat.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dongnebook.domain.chat.domain.ChatRoom;

public interface RoomRepository extends JpaRepository<ChatRoom,Long> {
	List<ChatRoom> findByMerchantIdOrCustomerId(Long merchantId, Long customerId);
}
