package com.dongnebook.domain.rental.exception;

import com.dongnebook.global.error.exception.BusinessException;
import com.dongnebook.global.error.exception.ErrorCode;

public class CanNotChangeStateException extends BusinessException {

	public CanNotChangeStateException() {
		super(ErrorCode.NOT_CHANGEABLE.getMessage(),ErrorCode.NOT_CHANGEABLE);
	}


}
