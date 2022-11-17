package com.dongnebook.domain.rental.exception;

import com.dongnebook.global.error.exception.BusinessException;
import com.dongnebook.global.error.exception.ErrorCode;

public class NotReturnableException extends BusinessException {

    public NotReturnableException() {
        super(ErrorCode.NOT_RETURNABLE.getMessage(), ErrorCode.NOT_RETURNABLE);
    }

}
