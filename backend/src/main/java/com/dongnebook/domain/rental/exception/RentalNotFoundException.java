package com.dongnebook.domain.rental.exception;

import com.dongnebook.global.error.exception.BusinessException;
import com.dongnebook.global.error.exception.ErrorCode;

public class RentalNotFoundException extends BusinessException {

    public RentalNotFoundException() {
        super(ErrorCode.RENTAL_NOT_FOUND.getMessage(), ErrorCode.RENTAL_NOT_FOUND);
    }

}
