package com.dongnebook.domain.chat.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

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


	@CreationTimestamp
	private LocalDateTime created_at;

	public ChatRoom(Member merchant, Member customer) {
		this.merchant = merchant;
		this.customer = customer;
	}

	public Member getPartner(Long id) {
		if (customer.hasSameId(id)) {
			return customer;
		}
		return merchant;
	}
}
