package com.dongnebook.domain.alarm.exception;

import com.dongnebook.global.error.exception.BusinessException;
import com.dongnebook.global.error.exception.ErrorCode;

public class AlarmNotFound extends BusinessException {

	public AlarmNotFound() {
		super("알람이 없어요", ErrorCode.ENTITY_NOT_FOUND);
	}
}
