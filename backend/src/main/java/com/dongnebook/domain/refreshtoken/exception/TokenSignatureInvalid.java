package com.dongnebook.domain.refreshtoken.exception;

import com.dongnebook.global.error.exception.BusinessException;
import com.dongnebook.global.error.exception.ErrorCode;

public class TokenSignatureInvalid extends BusinessException {

    public TokenSignatureInvalid() {
        super(ErrorCode.TOKEN_SIGNATURE_INVALID.getMessage(), ErrorCode.TOKEN_SIGNATURE_INVALID);
    }
}
