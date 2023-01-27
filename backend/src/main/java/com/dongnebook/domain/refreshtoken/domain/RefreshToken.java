package com.dongnebook.domain.refreshtoken.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.dongnebook.domain.model.BaseTimeEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long memberId;

    @Column(name = "rt_value")
    private String value;

    @Builder
    public RefreshToken(Long id, String value) {
        this.memberId = id;
        this.value = value;
    }

    public RefreshToken updateValue(String token) {
        this.value = token;
        return this;
    }
}
