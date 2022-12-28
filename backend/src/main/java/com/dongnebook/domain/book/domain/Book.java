package com.dongnebook.domain.book.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.AttributeConverter;
import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Converter;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import com.dongnebook.domain.book.dto.request.BookEditRequest;
import com.dongnebook.domain.book.dto.request.BookRegisterRequest;
import com.dongnebook.domain.book.exception.NotRentableException;
import com.dongnebook.domain.dibs.domain.Dibs;
import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.model.BaseTimeEntity;
import com.dongnebook.domain.model.Location;
import com.dongnebook.domain.rental.exception.CanNotChangeStateException;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "book", indexes = @Index(name = "idx_book", columnList = "latitude,longitude,book_state"))
public class Book extends BaseTimeEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Version
	@Column(name = "book_version")
	private Long version;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "author", nullable = false)
	private String author;

	@Column(name = "publisher", nullable = false)
	private String publisher;

	@Column(name = "img_url")
	private String imgUrl="asdfsadf";

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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@OneToMany(mappedBy = "book", cascade = CascadeType.REMOVE)
	private List<Dibs> dibsList = new ArrayList<>();

	@Builder
	public Book(Long id, String title, String author, String imgUrl, String publisher, String description, Money rentalFee,
		Location location, Member member) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.imgUrl = imgUrl;
		this.description = description;
		this.rentalFee = rentalFee;
		this.location = location;
		this.bookState = BookState.RENTABLE;
		this.member = member;
	}

	public void changeBookStateFromTo(BookState from, BookState to) {
		if (this.bookState.equals(from)) {
			this.bookState = to;
			return;
		}

		throw new CanNotChangeStateException();
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

	public void edit(BookEditRequest bookEditRequest) {
		this.imgUrl = bookEditRequest.getImageUrl() == null ? this.imgUrl : bookEditRequest.getImageUrl();
		this.description = bookEditRequest.getDescription();
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


