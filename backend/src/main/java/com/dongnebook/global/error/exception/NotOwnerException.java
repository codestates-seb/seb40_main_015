package com.dongnebook.global.error.exception;

public class NotOwnerException extends BusinessException{
	public NotOwnerException() {
		super(ErrorCode.NOT_OWNER.getMessage(), ErrorCode.NOT_OWNER);
	}
}
