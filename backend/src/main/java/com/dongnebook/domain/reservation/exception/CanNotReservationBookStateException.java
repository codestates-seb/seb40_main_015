package com.dongnebook.domain.reservation.exception;

import com.dongnebook.global.error.exception.BusinessException;
import com.dongnebook.global.error.exception.ErrorCode;

public class CanNotReservationBookStateException extends BusinessException {
    public CanNotReservationBookStateException() {
        super(ErrorCode.CANNOT_RESERVATION_BOOK_RENTAL_STATE.getMessage(), ErrorCode.CANNOT_RESERVATION_BOOK_RENTAL_STATE);
    }
}
