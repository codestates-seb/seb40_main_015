package com.dongnebook.global.error.exception;

import lombok.Getter;

@Getter
public abstract enum ErrorCode {

	INVALID_INPUT_VALUE(400, "잘못된 입력입니다."),
	ENTITY_NOT_FOUND(400, "엔티티를 찾을 수 없습니다."),
	INTERNAL_SERVER_ERROR(500, "서버 내부 오류"),
	HANDLE_ACCESS_DENIED(403, "접근이 거부 되었습니다."),
	METHOD_NOT_ALLOWED(405, "허용하지 않는 HTTP 메소드입니다."),


	/* Member */
	LOCATION_NOT_CREATED_YET(404,"아직 주거래 동네를 설정하지 않았습니다."),
	MEMBER_EXISTS(409, "이미 회원가입한 회원입니다"),

	/* Book */
	BOOK_NOT_FOUND(404, "해당 책을 찾지 못했습니다."),
	NOT_RENTABLE(404,"대여 가능 상태가 아닙니다");

	private final String message;
	private int status;

	ErrorCode(final int status, final String message) {
		this.message = message;
		this.status = status;
	}


}
