package com.dongnebook.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dongnebook.domain.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
