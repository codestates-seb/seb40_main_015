package com.dongnebook.domain.refreshtoken.exception;

import com.dongnebook.global.error.exception.BusinessException;
import com.dongnebook.global.error.exception.ErrorCode;

public class TokenEmpty extends BusinessException {

    public TokenEmpty() {
        super(ErrorCode.TOKEN_ILLEGAL_ARGUMENT.getMessage(), ErrorCode.TOKEN_ILLEGAL_ARGUMENT);
    }
}
