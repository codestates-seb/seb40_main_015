package com.dongnebook.domain.dibs.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.model.BaseTimeEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "dibs")
public class Dibs extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id",nullable = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "member_id",nullable = false)
	private Member member;

	@ManyToOne
	@JoinColumn(name = "book_id",nullable = false)
	private Book book;



	private Dibs(Member member, Book book) {
		this.member = member;
		this.book = book;
	}

	static public Dibs of(Member member, Book book){
		return new Dibs(member, book);
	}


}
