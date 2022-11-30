package com.dongnebook.domain.chat.domain;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RedisChat {

	@NotNull
	private Long roomId;

	@NotNull
	private Long senderId;

	@NotBlank
	private String message;

	private LocalDateTime createdAt;

	public RedisChat(@NotNull Long roomId, @NotNull Long senderId, @NotBlank String message,
		LocalDateTime createdAt) {
		this.roomId = roomId;
		this.senderId = senderId;
		this.message = message;
		this.createdAt = createdAt;
	}
}