package com.dongnebook.domain.book.adapter.in;

import java.util.List;

import org.springframework.http.HttpEntity;

import com.dongnebook.domain.book.application.port.in.response.ApiBookInfoResponse;

public interface BookInfoParser {
	List<ApiBookInfoResponse> toResponse(HttpEntity<String> httpEntity);
}
