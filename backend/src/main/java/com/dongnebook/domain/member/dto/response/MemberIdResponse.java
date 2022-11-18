package com.dongnebook.domain.member.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class MemberIdResponse {
    private Long id;

    @QueryProjection
    public MemberIdResponse(Long id) {
        this.id = id;
    }
}
