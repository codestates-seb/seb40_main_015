package com.dongnebook.domain.review.exception;

import com.dongnebook.global.error.exception.BusinessException;
import com.dongnebook.global.error.exception.ErrorCode;

public class BookRentalNotMatchException extends BusinessException {
    public BookRentalNotMatchException(){
        super(ErrorCode.BOOK_RENTAL_UNMATCHED.getMessage(), ErrorCode.BOOK_RENTAL_UNMATCHED);
    }
}
