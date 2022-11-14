package com.dongnebook.domain.member.exception;

import java.util.function.Supplier;

import com.dongnebook.global.error.exception.BusinessException;
import com.dongnebook.global.error.exception.ErrorCode;

import lombok.Getter;

public class LocationNotCreatedYetException extends BusinessException{

	public LocationNotCreatedYetException() {
		super(ErrorCode.LOCATION_NOT_CREATED_YET.getMessage(), ErrorCode.LOCATION_NOT_CREATED_YET);
	}


}
