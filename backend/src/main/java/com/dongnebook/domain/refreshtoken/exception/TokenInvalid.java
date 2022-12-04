package com.dongnebook.domain.refreshtoken.exception;

import com.dongnebook.global.error.exception.BusinessException;
import com.dongnebook.global.error.exception.ErrorCode;

public class TokenInvalid extends BusinessException {

    public TokenInvalid() {
        super(ErrorCode.TOKEN_INVALID.getMessage(), ErrorCode.TOKEN_INVALID);
    }
}
