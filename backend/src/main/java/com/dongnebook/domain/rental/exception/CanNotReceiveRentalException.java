package com.dongnebook.domain.rental.exception;

import com.dongnebook.global.error.exception.BusinessException;
import com.dongnebook.global.error.exception.ErrorCode;

public class CanNotReceiveRentalException extends BusinessException {

    public CanNotReceiveRentalException() {
        super(ErrorCode.CANNOT_RECEIVE_RENTAL.getMessage(), ErrorCode.CANNOT_RECEIVE_RENTAL);
    }

}
