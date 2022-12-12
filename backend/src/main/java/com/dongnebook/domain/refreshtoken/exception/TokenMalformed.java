package com.dongnebook.domain.refreshtoken.exception;

import com.dongnebook.global.error.exception.BusinessException;
import com.dongnebook.global.error.exception.ErrorCode;


public class TokenMalformed extends BusinessException {
    public TokenMalformed() {
        super(ErrorCode.TOKEN_MALFORMED.getMessage(), ErrorCode.TOKEN_MALFORMED);
    }
}
