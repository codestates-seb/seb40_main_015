package com.dongnebook.domain.dibs.exception;

import com.dongnebook.global.error.exception.BusinessException;
import com.dongnebook.global.error.exception.ErrorCode;

public class DibsNotFoundException extends BusinessException {

	public DibsNotFoundException() {
		super(ErrorCode.DIBS_NOT_FOUND.getMessage(), ErrorCode.DIBS_NOT_FOUND);
	}
}
