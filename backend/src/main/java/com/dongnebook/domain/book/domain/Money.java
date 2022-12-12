package com.dongnebook.domain.book.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Money {
	@Column(name = "rental_fee")
	Integer value;

	private Money(Integer value) {
		this.value = value;
	}

	public static Money of(final Integer value) {
		return new Money(value);
	}
}
