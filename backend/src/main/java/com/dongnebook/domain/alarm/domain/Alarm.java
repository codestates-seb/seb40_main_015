package com.dongnebook.domain.alarm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.member.domain.Member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Alarm {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "alarm_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne
	@JoinColumn(name = "book_id")
	private Book book;

	@Enumerated
	private AlarmType type;

	@Column(name = "is_read")
	private Boolean isRead;

	@Builder
	public Alarm(Member member, Book book, AlarmType type, Boolean isRead) {
		this.member = member;
		this.book = book;
		this.type = type;
		this.isRead = isRead;
	}

	static public Alarm create(Member member, Book book,AlarmType type){
		return Alarm.builder()
			.member(member)
			.book(book)
			.type(type)
			.isRead(false)
			.build();

	}
}
