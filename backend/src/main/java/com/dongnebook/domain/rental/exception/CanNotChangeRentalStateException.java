package com.dongnebook.domain.rental.exception;

import com.dongnebook.global.error.exception.BusinessException;
import com.dongnebook.global.error.exception.ErrorCode;

public class CanNotChangeRentalStateException extends BusinessException {

    public CanNotChangeRentalStateException(){
        super(ErrorCode.CANNOT_CHANGE_RENTAL_STATE.getMessage(), ErrorCode.CANNOT_CHANGE_RENTAL_STATE);
    }

}
