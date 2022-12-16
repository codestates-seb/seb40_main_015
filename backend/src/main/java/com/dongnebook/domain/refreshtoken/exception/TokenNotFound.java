package com.dongnebook.domain.refreshtoken.exception;

import com.dongnebook.global.error.exception.BusinessException;
import com.dongnebook.global.error.exception.ErrorCode;

public class TokenNotFound extends BusinessException {
    public TokenNotFound() {
        super(ErrorCode.ACCESS_TOKEN_NOT_FOUND.getMessage(), ErrorCode.ACCESS_TOKEN_NOT_FOUND);
    }
}
