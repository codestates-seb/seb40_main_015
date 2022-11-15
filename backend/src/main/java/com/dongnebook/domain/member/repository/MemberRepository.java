package com.dongnebook.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dongnebook.domain.member.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUserId(String userId);
}
