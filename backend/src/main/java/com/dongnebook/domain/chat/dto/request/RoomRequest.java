package com.dongnebook.domain.chat.dto.request;

import javax.validation.constraints.NotNull;

import lombok.Getter;

@Getter
public class RoomRequest {

	@NotNull
	private Long merchantId;
	@NotNull
	private Long customerId;
	@NotNull
	private Long bookId;

	public RoomRequest(Long merchantId, Long customerId, Long bookId) {
		this.merchantId = merchantId;
		this.customerId = customerId;
		this.bookId = bookId;
	}
}

