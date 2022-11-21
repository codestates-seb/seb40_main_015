package com.dongnebook.global.error.exception;



import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{

	private ErrorCode errorCode;

	public BusinessException(String message, ErrorCode errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public BusinessException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}



	public ErrorCode getErrorCode() {
		return errorCode;
	}
}
