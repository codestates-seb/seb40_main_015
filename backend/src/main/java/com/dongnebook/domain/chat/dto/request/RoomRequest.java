package com.dongnebook.domain.chat.dto.request;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RoomRequest {

	@NotNull
	private Long merchantId;
	@NotNull
	private Long customerId;

	public RoomRequest(Long merchantId, Long customerId) {
		this.merchantId = merchantId;
		this.customerId = customerId;
	}
}

