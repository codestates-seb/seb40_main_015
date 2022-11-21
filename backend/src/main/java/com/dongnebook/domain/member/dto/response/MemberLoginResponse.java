package com.dongnebook.domain.member.dto.response;

import com.dongnebook.domain.model.Location;
import com.dongnebook.global.config.security.auth.userdetails.AuthMember;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberLoginResponse {
    private Long id;
    private String userId;
    private String nickName;
    private Location location;

    @Builder
    public MemberLoginResponse(Long id, String userId, String nickName, Location location) {
        this.id = id;
        this.userId = userId;
        this.nickName = nickName;
        this.location = location;
    }

    public static MemberLoginResponse of(AuthMember authMember){
        return MemberLoginResponse.builder()
            .id(authMember.getId())
            .userId(authMember.getUserId())
            .nickName(authMember.getNickname())
            .location(authMember.getLocation())
            .build();
    }
}
