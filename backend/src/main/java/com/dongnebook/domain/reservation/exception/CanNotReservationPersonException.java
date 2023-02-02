package com.dongnebook.domain.reservation.exception;

import com.dongnebook.global.error.exception.BusinessException;
import com.dongnebook.global.error.exception.ErrorCode;

public class CanNotReservationPersonException extends BusinessException {
    public CanNotReservationPersonException() {
        super(ErrorCode.CANNOT_RESERVATION_PERSON.getMessage(), ErrorCode.CANNOT_RESERVATION_PERSON);
    }
}
