package com.dongnebook.domain.book.exception;

import com.dongnebook.global.error.exception.BusinessException;
import com.dongnebook.global.error.exception.ErrorCode;

public class CanNotChangeBookStateException extends BusinessException {

    public CanNotChangeBookStateException() {
        super(ErrorCode.CANNOT_CHANGE_BOOK_STATE.getMessage(), ErrorCode.CANNOT_CHANGE_BOOK_STATE);
    }

}
