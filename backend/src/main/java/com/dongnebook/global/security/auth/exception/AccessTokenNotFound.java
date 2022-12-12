package com.dongnebook.global.security.auth.exception;

import com.dongnebook.global.error.exception.BusinessException;
import com.dongnebook.global.error.exception.ErrorCode;

public class AccessTokenNotFound extends BusinessException {
	public AccessTokenNotFound() {
		super(ErrorCode.ACCESS_TOKEN_NOT_FOUND.getMessage(),ErrorCode.ACCESS_TOKEN_NOT_FOUND);
	}
}
