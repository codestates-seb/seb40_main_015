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

import com.dongnebook.domain.dibs.domain.Dibs;
import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.model.BaseTimeEntity;
import com.dongnebook.domain.model.Location;
import com.dongnebook.global.error.exception.CanNotChangeStateException;
import com.dongnebook.global.error.exception.NotOwnerException;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "book", indexes = {
	@Index(name = "idx_book", columnList = "latitude,longitude,book_state"),
	@Index(name = "idx_book2", columnList = "book_state")})
public class Book extends BaseTimeEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Version
	@Column(name = "book_version")
	private Long version;

	@Embedded
	private BookProduct bookProduct;

	@Column(name = "img_url")
	private String imgUrl = "Default";

	@Lob
	@Column(name = "description")
	private String description;

	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "rental_fee"))
	private Money rentalFee;

	@Embedded
	private Location location;

	@Convert(converter = BookStateConverter.class)
	@Column(name = "book_state", length = 20)
	private BookState bookState;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;


	@OneToMany(mappedBy = "book", cascade = CascadeType.REMOVE)
	private List<Dibs> dibsList = new ArrayList<>();

	@Builder
	public Book(Long id, BookProduct bookProduct, String imgUrl, String description, Money rentalFee,
		Location location, Member member) {
		this.id = id;
		this.bookProduct = bookProduct;
		this.imgUrl = imgUrl;
		this.description = description;
		this.rentalFee = rentalFee;
		this.location = location;
		this.bookState = BookState.RENTABLE;
		this.member = member;
	}

	public Book changeBookStateFromTo(BookState from, BookState to) {
		if (this.bookState.equals(from)) {
			this.bookState = to;
			return this;
		}
		throw new CanNotChangeStateException();
	}

	public void edit(String imgUrl, String description, Long memberId) {
		if (!isMyBook(memberId)) {
			throw new NotOwnerException();
		}
		Objects.requireNonNullElse(imgUrl, this.imgUrl);
		this.description = description;
	}

	public static Book create(BookProduct bookProduct, String imgUrl, String description, Money money, Location location, Member member) {
		return Book.builder()
			.bookProduct(bookProduct)
			.description(description)
			.imgUrl(imgUrl)
			.rentalFee(money)
			.location(location)
			.member(member)
			.build();
	}

	public void delete(Long requestMemberId) {
		if (!isMyBook(requestMemberId)) {
			throw new NotOwnerException();
		}
		if (Objects.equals(this.bookState, BookState.RENTABLE)) {
			this.bookState = BookState.DELETED;
			return;
		}
		throw new NotRentableException();
	}

	private boolean isMyBook(Long requestMemberId) {
		return Objects.equals(postOwnerId(), requestMemberId);
	}

	private Long postOwnerId() {
		return this.member.getId();
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


