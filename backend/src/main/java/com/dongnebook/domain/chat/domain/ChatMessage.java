package com.dongnebook.domain.chat.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.model.Location;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "chat_message")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_chat_to_sender"))
	private Member sender;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_room_to_receiver"))
	private Member receiver;

	@Column(nullable = false)
	private String content;

	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(nullable = false, name = "room")
	private ChatRoom room;

	@CreationTimestamp
	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Builder
	public ChatMessage(Member sender, Member receiver, String content, ChatRoom room) {
		this.sender = sender;
		this.receiver = receiver;
		this.content = content;
		this.room = room;
	}
}
