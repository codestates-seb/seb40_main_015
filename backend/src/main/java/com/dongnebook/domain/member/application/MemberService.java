package com.dongnebook.domain.member.application;

import org.springframework.stereotype.Service;

import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.member.dto.request.MemberRegisterRequest;
import com.dongnebook.domain.member.repository.MemberRepository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	public Long create(MemberRegisterRequest memberRegisterRequest) {
		Member member = Member.create(memberRegisterRequest);
		return memberRepository.save(member).getId();
	}
}
