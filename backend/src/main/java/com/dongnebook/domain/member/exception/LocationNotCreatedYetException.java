package com.dongnebook.domain.member.exception;

import com.dongnebook.global.error.exception.BusinessException;
import com.dongnebook.global.error.exception.ErrorCode;

public class LocationNotCreatedYetException extends BusinessException{
	public LocationNotCreatedYetException() {
		super(ErrorCode.LOCATION_NOT_CREATED_YET.getMessage(), ErrorCode.LOCATION_NOT_CREATED_YET);
	}
}
