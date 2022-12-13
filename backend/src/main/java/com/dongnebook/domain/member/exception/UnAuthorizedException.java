package com.dongnebook.domain.member.exception;

import com.dongnebook.global.error.exception.BusinessException;
import com.dongnebook.global.error.exception.ErrorCode;

public class UnAuthorizedException extends BusinessException {
    public UnAuthorizedException() {
        super(ErrorCode.UN_AUTHORIZED.getMessage(), ErrorCode.UN_AUTHORIZED);
    }
}
