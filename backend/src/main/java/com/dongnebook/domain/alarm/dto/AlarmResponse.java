package com.dongnebook.domain.alarm.dto;

import com.dongnebook.global.enums.AlarmType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AlarmResponse {
	private Long alarmId;
	private AlarmType alarmType;
	private Long merchantId;
	private String bookTitle;
	private Boolean isRead;

	@QueryProjection
	public AlarmResponse(Long alarmId, AlarmType alarmType, Long merchantId, String bookTitle, Boolean isRead) {
		this.alarmId = alarmId;
		this.alarmType = alarmType;
		this.merchantId = merchantId;
		this.bookTitle = bookTitle;
		this.isRead = isRead;
	}
}
