package com.dongnebook.domain.chat.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.member.domain.Member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_room_to_merchant"))
	private Member merchant;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_room_to_customer"))
	private Member customer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_room_to_book"))
	private Book book;


	@CreationTimestamp
	@Column(name = "created_at")
	private LocalDateTime createdAt;

	public ChatRoom(Member merchant, Member customer, Book book) {
		this.merchant = merchant;
		this.customer = customer;
		this.book = book;
	}

	public Member getPartner(Long id) {
		if (customer.hasSameId(id)) {
			return merchant;
		}
		return customer;
	}
}
