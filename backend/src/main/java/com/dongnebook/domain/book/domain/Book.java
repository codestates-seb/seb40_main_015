package com.dongnebook.domain.book.domain;

import java.util.Objects;

import javax.persistence.AttributeConverter;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Converter;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.dongnebook.domain.book.dto.request.BookRegisterRequest;
import com.dongnebook.domain.book.exception.NotRentableException;
import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.model.BaseTimeEntity;
import com.dongnebook.domain.model.Location;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false)
	private Long id;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "author", nullable = false)
	private String author;

	@Column(name = "publisher", nullable = false)
	private String publisher;

	@Column(name = "img_url")
	private String ImgUrl;

	@Lob
	@Column(name = "description")
	private String description;

	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "rental_fee"))
	private Money rentalFee;

	@Embedded
	private Location location;


	@Convert(converter = BookStateConverter.class)
	@Column(name = "book_state")
	private BookState bookState;

	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@Builder
	public Book(String title, String author, String publisher, String imgUrl, String description, Money rentalFee,
		Location location, BookState bookState, Member member) {
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.ImgUrl = imgUrl;
		this.description = description;
		this.rentalFee = rentalFee;
		this.location = location;
		this.bookState = bookState;
		this.member = member;
	}

	public void changeBookState(BookState bookState) {
		this.bookState = bookState;
	}


	public static Book create(BookRegisterRequest bookRegisterRequest, Location location, Member member) {
		return Book.builder()
			.title(bookRegisterRequest.getTitle())
			.author(bookRegisterRequest.getAuthor())
			.imgUrl(bookRegisterRequest.getImageUrl())
			.publisher(bookRegisterRequest.getPublisher())
			.description(bookRegisterRequest.getDescription())
			.rentalFee(Money.of(bookRegisterRequest.getRentalFee()))
			.location(location)
			.bookState(BookState.RENTABLE)
			.member(member)
			.build();
	}

	public void delete() {

		if (Objects.equals(this.bookState, BookState.RENTABLE)) {
			this.bookState = BookState.DELETED;
			return;
		}
		throw new NotRentableException();
	}
}
@Converter
class BookStateConverter implements AttributeConverter<BookState, String> {

	@Override
	public String convertToDatabaseColumn(BookState attribute) {
		return String.valueOf(attribute);
	}

	@Override
	public BookState convertToEntityAttribute(String dbData) {
		return BookState.valueOf(dbData);
	}

}


