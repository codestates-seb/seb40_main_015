package com.dongnebook.domain.book.domain;

import lombok.Getter;

@Getter
public enum BookState {
	RENTABLE("대여가능"),
	TRADING("거래중"),
	UNRENTABLE_UNRESERVABLE("대여중&예약불가"),
	UNRENTABLE_RESERVABLE("대여중&예약가능"),
	DELETED("거래중단");

	private String message;

	BookState(String message) {
		this.message = message;
	}


}
