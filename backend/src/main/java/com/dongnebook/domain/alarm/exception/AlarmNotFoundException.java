package com.dongnebook.domain.alarm.exception;

import com.dongnebook.global.error.exception.BusinessException;
import com.dongnebook.global.error.exception.ErrorCode;

public class AlarmNotFoundException extends BusinessException {
	public AlarmNotFoundException() {
		super("알람이 없어요", ErrorCode.ENTITY_NOT_FOUND);
	}
}
