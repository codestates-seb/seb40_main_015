package com.dongnebook.global.error.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

	INVALID_INPUT_VALUE(400, "잘못된 입력입니다."),
	ENTITY_NOT_FOUND(400, "엔티티를 찾을 수 없습니다."),
	INTERNAL_SERVER_ERROR(500, "서버 내부 오류"),
	HANDLE_ACCESS_DENIED(403, "접근이 거부 되었습니다."),
	METHOD_NOT_ALLOWED(405, "허용하지 않는 HTTP 메소드입니다."),
	NOT_OWNER(404, "주인이 아닙니다."),

	/* JWT */
	ACCESS_TOKEN_NOT_FOUND(404,"액세스토큰을 찾을 수 없습니다."),
	TOKEN_EXPIRED(400, "Token Expired"),
	TOKEN_INVALID(400, "Token Invalid"),
	TOKEN_SIGNATURE_INVALID(400, "Token Signature Invalid"),
	TOKEN_MALFORMED(400, "Token Malformed"),
	TOKEN_UNSUPPORTED(400, "Token Unsupported"),
	TOKEN_ILLEGAL_ARGUMENT(400, "Token Illegal Argument"),

	/* Member */
	LOCATION_NOT_CREATED_YET(404,"아직 주거래 동네를 설정하지 않았습니다."),
	UNAUTHORIZIZED_ACCESS(401, "접근 권한 인증이 되지 않았습니다."),
	MEMBER_HAS_BOOK_ON_LOAN(404, "대여중인 책이 있습니다"),

	MEMBER_NOT_FOUND(404, "해당 유저를 찾지 못했습니다."),


	/* Book */
	BOOK_NOT_FOUND(404, "해당 책을 찾지 못했습니다."),

	/* Rental */
	CANNOT_RENT_MY_BOOK(404, "본인의 책은 대여할 수 없습니다."),
	CANNOT_CANCEL_RENTAL(404, "대여 취소 가능 대상이 아닙니다."),
	CANNOT_RECEIVE_RENTAL(404, "도서 수령 가능 대상이 아닙니다."),
	CANNOT_RETURN_RENTAL(404, "도서 반납 가능 대상이 아닙니다."),
	NOT_RENTABLE(404,"대여 가능 상태가 아닙니다."),
	RENTAL_NOT_FOUND(404, "해당 대여 건을 찾지 못했습니다."),
	NOT_CANCELABLE(404, "대여 취소 가능한 상태가 아닙니다."),
  	NOT_RECEIVABLE(404, "도서 수령 가능 상태가 아닙니다."),
	NOT_RETURNABLE(404, "도서 반납 가능 상태가 아닙니다."),
	NOT_CHANGEABLE(404,"상태를 바꿀수 없습니다."),

	/* Dibs */
	DIBS_NOT_FOUND(404, "이 책을 찜한적이 없습니다."),

	/* Reservation */
	RESERVATION_NOT_FOUND(404, "예약 건을 찾지 못했습니다."),
	CANNOT_RESERVATION_BOOK_RENTAL_STATE(404, "예약할 수 있는 도서상태 또는 대여상태가 아닙니다."),
	CANNOT_RESERVATION_PERSON(404, "예약할 수 있는 회원이 아닙니다."),
	NOT_CHANGEABLE_RESERVATION_STATE(404, "변경할 수 있는 예약상태가 아닙니다"),

	/* Review */
	BOOK_RENTAL_UNMATCHED(404, "도서정보와 대여도서정보가 일치하지 않습니다"),

	/* Image */
	UPLOAD_FAILED(404, "업로드 실패!!!!!!");






	private final String message;
	private int status;

	ErrorCode(final int status, final String message) {
		this.message = message;
		this.status = status;
	}


}
