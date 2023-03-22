package com.dongnebook.domain.alarm.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.dongnebook.domain.alarm.dto.QAlarmResponse is a Querydsl Projection type for AlarmResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QAlarmResponse extends ConstructorExpression<AlarmResponse> {

    private static final long serialVersionUID = 854829977L;

    public QAlarmResponse(com.querydsl.core.types.Expression<Long> alarmId, com.querydsl.core.types.Expression<com.dongnebook.global.enums.AlarmType> alarmType, com.querydsl.core.types.Expression<Long> merchantId, com.querydsl.core.types.Expression<String> bookTitle, com.querydsl.core.types.Expression<Boolean> isRead) {
        super(AlarmResponse.class, new Class<?>[]{long.class, com.dongnebook.global.enums.AlarmType.class, long.class, String.class, boolean.class}, alarmId, alarmType, merchantId, bookTitle, isRead);
    }

}

