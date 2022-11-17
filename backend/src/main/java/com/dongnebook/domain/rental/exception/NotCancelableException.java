package com.dongnebook.domain.rental.exception;

import com.dongnebook.global.error.exception.BusinessException;
import com.dongnebook.global.error.exception.ErrorCode;

public class NotCancelableException extends BusinessException {

    public NotCancelableException() {
        super(ErrorCode.NOT_CANCELABLE.getMessage(), ErrorCode.NOT_CANCELABLE);
    }

}
