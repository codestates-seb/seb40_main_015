package com.dongnebook.global.config.security.auth.oauth;

import com.dongnebook.domain.member.domain.Member;

public class OAuthUserProfile {
    private final String name;
    private final String email;
    private final String oauthId;

    public OAuthUserProfile(String name, String email, String oauthId) {
        this.name = name;
        this.email = email;
        this.oauthId = oauthId;
    }

    public Member createOauth2Member() {
        return Member.builder()
                .userId(email)
                .nickname(name)
                .password(oauthId)
                .build();
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
