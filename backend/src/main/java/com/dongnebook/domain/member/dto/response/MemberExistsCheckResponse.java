package com.dongnebook.domain.member.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberExistsCheckResponse {
    private boolean success;
    private String message;

    @Builder
    public MemberExistsCheckResponse(Boolean success, String message){
        this.success = success;
        this.message = message;
    }
}
