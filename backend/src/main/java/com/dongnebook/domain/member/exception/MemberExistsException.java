package com.dongnebook.domain.member.exception;

import com.dongnebook.global.error.exception.BusinessException;
import com.dongnebook.global.error.exception.ErrorCode;

public class MemberExistsException extends BusinessException {
    public MemberExistsException() {
        super(ErrorCode.MEMBER_EXISTS.getMessage(), ErrorCode.MEMBER_EXISTS);
    }
}
