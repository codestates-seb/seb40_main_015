package com.dongnebook.domain.member.exception;

import com.dongnebook.global.error.exception.BusinessException;
import com.dongnebook.global.error.exception.ErrorCode;

public class MemberHasOnLoanException extends BusinessException {
	public MemberHasOnLoanException() {
		super(ErrorCode.MEMBER_HAS_BOOK_ON_LOAN.getMessage(), ErrorCode.MEMBER_HAS_BOOK_ON_LOAN);
	}
}
