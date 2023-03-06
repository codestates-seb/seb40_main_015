package com.dongnebook.domain.member.dto.response;

import com.dongnebook.domain.model.Location;
import com.dongnebook.global.security.auth.userdetails.AuthMember;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberLoginResponse {
    private final Long id;
    private final String userId;
    private final String nickname;
    private final Location location;

    @Builder
    public MemberLoginResponse(Long id, String userId, String nickname, Location location) {
        this.id = id;
        this.userId = userId;
        this.nickname = nickname;
        this.location = location;
    }

    public static MemberLoginResponse of(AuthMember authMember){
        return MemberLoginResponse.builder()
            .id(authMember.getMemberId())
            .userId(authMember.getUserId())
            .nickname(authMember.getNickname())
            .location(authMember.getLocation())
            .build();
    }
}
