package com.dongnebook.domain.rental.exception;

import com.dongnebook.global.error.exception.BusinessException;
import com.dongnebook.global.error.exception.ErrorCode;

public class NotReceivableException extends BusinessException {

    public NotReceivableException() {
        super(ErrorCode.NOT_RECEIVABLE.getMessage(), ErrorCode.NOT_RECEIVABLE);
    }

}
