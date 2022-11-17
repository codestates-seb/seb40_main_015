package com.dongnebook.domain.rental.exception;

import com.dongnebook.global.error.exception.BusinessException;
import com.dongnebook.global.error.exception.ErrorCode;

public class CanNotCancelRentalException extends BusinessException {

    public CanNotCancelRentalException() {
        super(ErrorCode.CANNOT_CANCEL_RENTAL.getMessage(), ErrorCode.CANNOT_CANCEL_RENTAL);
    }

}
