package com.dongnebook.domain.book.exception;

import com.dongnebook.global.error.exception.BusinessException;
import com.dongnebook.global.error.exception.ErrorCode;

public class NotRentableException extends BusinessException {

	public NotRentableException() {
		super(ErrorCode.NOT_RENTABLE.getMessage(), ErrorCode.NOT_RENTABLE);
	}
}
