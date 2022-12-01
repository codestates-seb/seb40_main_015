package com.dongnebook.domain.chat.domain;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

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
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime createdAt;

	public RedisChat(@NotNull Long roomId, @NotNull Long senderId, @NotBlank String message,
		LocalDateTime createdAt) {
		this.roomId = roomId;
		this.senderId = senderId;
		this.message = message;
		this.createdAt = createdAt;
	}
}