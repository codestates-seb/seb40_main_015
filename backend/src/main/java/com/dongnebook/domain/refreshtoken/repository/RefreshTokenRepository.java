package com.dongnebook.domain.refreshtoken.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dongnebook.domain.refreshtoken.domain.RefreshToken;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    // member id 값으로 refresh token 찾기
    Optional<RefreshToken> findByMemberId(String memberId);
    // value 값으로 key 찾기
    Optional<RefreshToken> findByValue(String value);
    // key 값으로 refresh token 삭제
    void deleteByMemberId(Long memberId);
    // value 값으로 refresh token 삭제
    void deleteByValue(String value);

}
