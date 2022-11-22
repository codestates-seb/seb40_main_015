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
import lombok.NoArgsConstructor;

@Entity
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

}
