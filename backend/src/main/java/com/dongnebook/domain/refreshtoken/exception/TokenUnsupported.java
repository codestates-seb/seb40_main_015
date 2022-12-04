package com.dongnebook.domain.refreshtoken.exception;

import com.dongnebook.global.error.exception.BusinessException;
import com.dongnebook.global.error.exception.ErrorCode;

public class TokenUnsupported extends BusinessException {

    public TokenUnsupported() {
        super(ErrorCode.TOKEN_UNSUPPORTED.getMessage(), ErrorCode.TOKEN_UNSUPPORTED);
    }
}
