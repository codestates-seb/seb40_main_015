package com.dongnebook.domain.rental.exception;

import com.dongnebook.global.error.exception.BusinessException;
import com.dongnebook.global.error.exception.ErrorCode;

public class CanNotRentMyBookException extends BusinessException {
    public CanNotRentMyBookException() {
        super(ErrorCode.CANNOT_RENT_MY_BOOK.getMessage(), ErrorCode.CANNOT_RENT_MY_BOOK);
    }
}
