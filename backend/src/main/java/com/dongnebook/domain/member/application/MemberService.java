package com.dongnebook.domain.member.application;

import org.springframework.stereotype.Service;

import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.member.dto.request.MemberRegisterRequest;
import com.dongnebook.domain.member.repository.MemberRepository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import static com.dongnebook.global.error.exception.ErrorCode.MEMBER_EXISTS;

@Slf4j
@Getter
@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	public Long create(MemberRegisterRequest memberRegisterRequest) {
		Member member = Member.create(memberRegisterRequest);
		return memberRepository.save(member).getId();
	}

	private void verifyExistMemberByUserId(String userId) {
		Optional<Member> member = memberRepository.findByUserId(userId);
		if (member.isPresent())
			throw new MemberExistsException();
	}

	private void verifyExistMemberByNickName(String nickname) {
		Optional<Member> member = memberRepository.findByUserId(nickname);
		if (member.isPresent())
			throw new MemberExistsException();
	}
}
