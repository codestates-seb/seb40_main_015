package com.dongnebook.domain.chat.domain;

import java.time.LocalDateTime;
import java.util.Objects;

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

import com.dongnebook.domain.member.domain.Member;

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


	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Builder
	public ChatMessage(Member sender, Member receiver, String content, ChatRoom room, LocalDateTime createdAt) {
		this.sender = sender;
		this.receiver = receiver;
		this.content = content;
		this.room = room;
		this.createdAt = createdAt;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		ChatMessage that = (ChatMessage)o;
		return Objects.equals(sender.getId(), that.sender.getId())
			&& Objects.equals(receiver.getId(), that.receiver.getId()) && Objects.equals(content, that.content)
			&& Objects.equals(createdAt, that.createdAt);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, sender, receiver, content, room, createdAt);
	}
}
