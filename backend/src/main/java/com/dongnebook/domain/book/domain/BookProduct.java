package com.dongnebook.domain.book.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
public class BookProduct implements Serializable {

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "author", nullable = false)
	private String author;

	@Column(name = "publisher", nullable = false)
	private String publisher;

	public BookProduct(String title, String author, String publisher) {
		this.title = title;
		this.author = author;
		this.publisher = publisher;
	}

	public static BookProduct of(String title, String author, String publisher) {
		return new BookProduct(title, author, publisher);
	}

}
