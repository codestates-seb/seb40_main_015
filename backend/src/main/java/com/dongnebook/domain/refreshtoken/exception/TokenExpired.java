package com.dongnebook.domain.refreshtoken.exception;

import com.dongnebook.global.error.exception.BusinessException;
import com.dongnebook.global.error.exception.ErrorCode;

public class TokenExpired extends BusinessException {

    public TokenExpired() {
        super(ErrorCode.TOKEN_EXPIRED.getMessage(),ErrorCode.TOKEN_EXPIRED);
    }
}
