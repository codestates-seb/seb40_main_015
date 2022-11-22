package com.dongnebook.global.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MultiResponse<T> {

	private T data;

	private MultiResponse(T data) {
		this.data = data;
	}

	static public <T> MultiResponse<T> of(T data){
		return new MultiResponse<>(data);

	}
}
