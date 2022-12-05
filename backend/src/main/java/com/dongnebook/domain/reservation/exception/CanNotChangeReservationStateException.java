package com.dongnebook.domain.reservation.exception;

import com.dongnebook.global.error.exception.BusinessException;
import com.dongnebook.global.error.exception.ErrorCode;

public class CanNotChangeReservationStateException extends BusinessException {

    public CanNotChangeReservationStateException(){
        super(ErrorCode.NOT_CHANGEABLE_RESERVATION_STATE.getMessage(), ErrorCode.NOT_CHANGEABLE_RESERVATION_STATE);
    }

}
