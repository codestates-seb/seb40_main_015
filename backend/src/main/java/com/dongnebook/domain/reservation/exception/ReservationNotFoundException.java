package com.dongnebook.domain.reservation.exception;

import com.dongnebook.global.error.exception.BusinessException;
import com.dongnebook.global.error.exception.ErrorCode;

public class ReservationNotFoundException extends BusinessException {

    public ReservationNotFoundException(){
        super(ErrorCode.RESERVATION_NOT_FOUND.getMessage(), ErrorCode.RESERVATION_NOT_FOUND);
    }

}
