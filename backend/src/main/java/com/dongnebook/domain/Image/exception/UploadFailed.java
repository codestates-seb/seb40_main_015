package com.dongnebook.domain.Image.exception;

import com.dongnebook.global.error.exception.BusinessException;
import com.dongnebook.global.error.exception.ErrorCode;

public class UploadFailed extends BusinessException {
	public UploadFailed() {
		super(ErrorCode.UPLOAD_FAILED.getMessage(),ErrorCode.UPLOAD_FAILED);
	}
}
