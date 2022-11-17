package com.dongnebook.domain.rental.exception;

import com.dongnebook.global.error.exception.BusinessException;
import com.dongnebook.global.error.exception.ErrorCode;

public class CanNotReturnRentalException extends BusinessException {

    public CanNotReturnRentalException() {
        super(ErrorCode.CANNOT_RETURN_RENTAL.getMessage(), ErrorCode.CANNOT_RETURN_RENTAL);
    }

}
