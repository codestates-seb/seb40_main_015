package com.dongnebook.domain.alarm.exception;

import com.dongnebook.global.error.exception.BusinessException;
import com.dongnebook.global.error.exception.ErrorCode;

public class CanNotSendException extends BusinessException {
	public CanNotSendException() {
		super(ErrorCode.CAN_NOT_SEND.getMessage(), ErrorCode.CAN_NOT_SEND);
	}
}
