package com.dongnebook.support;

import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.book.domain.BookState;
import com.dongnebook.domain.book.domain.Money;
import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.model.Location;

public enum BookStub {
	BOOK1(1L, "자바의 정석", "남궁성", "도우출판", "이미지 주소", "자바의 정석입니다", Money.of(1000), MemberStub.MEMBER1.of(1L).getLocation(),
		BookState.RENTABLE, MemberStub.MEMBER1.of(1L)),
	BOOK2(2L, "씨의 정석", "남궁성", "도우출판", "이미지 주소", "씨의 정석입니다", Money.of(1000), MemberStub.MEMBER2.of(2L).getLocation(),
		BookState.RENTABLE, MemberStub.MEMBER2.of(2L)),
	BOOK3(3L, "파이썬의 정석", "남궁성", "도우출판", "이미지 주소", "파이썬의 정석입니다", Money.of(1000), MemberStub.MEMBER3.of(3L).getLocation(),
		BookState.RENTABLE, MemberStub.MEMBER3.of(3L)),
	BOOK4(4L, "스프링의 정석", "남궁성", "도우출판", "이미지 주소", "스프링의 정석입니다", Money.of(1000), MemberStub.MEMBER4.of(4L).getLocation(),
		BookState.RENTABLE, MemberStub.MEMBER4.of(4L)),
	BOOK5(5L, "노드js의 정석", "남궁성", "도우출판", "이미지 주소", "노드js의 정석입니다", Money.of(1000), MemberStub.MEMBER5.of(5L).getLocation(),
		BookState.RENTABLE, MemberStub.MEMBER5.of(5L)),
	BOOK6(6L, "리액트의 정석", "남궁성", "도우출판", "이미지 주소", "리액트의 정석입니다", Money.of(1000), MemberStub.MEMBER6.of(6L).getLocation(),
		BookState.RENTABLE, MemberStub.MEMBER6.of(6L)),
	BOOK7(7L, "Mysql의 정석", "남궁성", "도우출판", "이미지 주소", "Mysql의 정석입니다", Money.of(1000), MemberStub.MEMBER7.of(7L).getLocation(),
		BookState.RENTABLE, MemberStub.MEMBER7.of(7L));

	private Long id;
	private String title;
	private String author;
	private String publisher;
	private String imgUrl;
	private String description;
	private Money rentalFee;
	private Location location;
	private BookState bookState;
	private Member member;

	BookStub(Long id, String title, String author, String publisher, String imgUrl, String description, Money rentalFee,
		Location location, BookState bookState, Member member) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.imgUrl = imgUrl;
		this.description = description;
		this.rentalFee = rentalFee;
		this.location = location;
		this.bookState = bookState;
		this.member = member;
	}

	public Book of(Long bookId) {
		return Book.builder()
			.id(bookId)
			.location(this.location)
			.rentalFee(this.rentalFee)
			.description(this.description)
			.publisher(this.publisher)
			.imgUrl(this.imgUrl)
			.author(this.author)
			.title(this.title)
			.member(this.member)
			.build();
	}

	public Book of() {
		return Book.builder()
			.id(null)
			.location(this.location)
			.rentalFee(this.rentalFee)
			.description(this.description)
			.publisher(this.publisher)
			.imgUrl(this.imgUrl)
			.author(this.author)
			.title(this.title)
			.member(this.member)
			.build();
	}
	}
