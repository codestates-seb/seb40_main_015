package com.dongnebook.domain.book.exception;

import com.dongnebook.global.error.exception.BusinessException;
import com.dongnebook.global.error.exception.ErrorCode;

public class BookNotFoundException extends BusinessException {

	public BookNotFoundException() {
		super(ErrorCode.BOOK_NOT_FOUND.getMessage(), ErrorCode.BOOK_NOT_FOUND);
	}
}
