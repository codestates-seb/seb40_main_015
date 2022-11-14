package com.dongnebook.domain.book.domain;

public enum BookState {
	RENTABLE("대여가능"),
	TRADING("거래중"),
	UNRENTABLE_UNRESERVABLE("대여가능&예약불가"),
	UNRENTABLE_RESERVABLE("대여가능&예약가능"),
	DELETED("거래 중단");

	private String message;

	BookState(String message) {
		this.message = message;
	}


}
