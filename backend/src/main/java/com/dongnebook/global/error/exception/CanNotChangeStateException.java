package com.dongnebook.global.error.exception;

public class CanNotChangeStateException extends BusinessException {
	public CanNotChangeStateException() {
		super(ErrorCode.NOT_CHANGEABLE.getMessage(), ErrorCode.NOT_CHANGEABLE);
	}
}
