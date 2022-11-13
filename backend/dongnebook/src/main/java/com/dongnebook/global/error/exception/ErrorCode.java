package com.dongnebook.global.error.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

	INVALID_INPUT_VALUE(400, "Invalid Input Value"),
	ENTITY_NOT_FOUND(400, " Entity Not Found"),
	INTERNAL_SERVER_ERROR(500, "Server Error"),
	HANDLE_ACCESS_DENIED(403, "Access is Denied"),
	METHOD_NOT_ALLOWED(405, " Invalid Input Value"),
	CONSTRAINTS_VIOLATED(404, "Constraints Violated"),

	/* Book */
	BOOK_NOT_FOUND(404, "Book Not Found");

	private final String message;
	private int status;

	ErrorCode(final int status, final String message) {
		this.message = message;
		this.status = status;
	}


}
