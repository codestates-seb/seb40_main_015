package com.dongnebook.domain.member.application;

import com.dongnebook.domain.member.dto.response.MemberIdResponse;
import com.dongnebook.domain.member.exception.MemberExistsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.member.dto.request.MemberRegisterRequest;
import com.dongnebook.domain.member.repository.MemberRepository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Getter
@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public Long create(MemberRegisterRequest memberRegisterRequest) {
		Member member = Member.create(memberRegisterRequest);
		member.setPassword(passwordEncoder.encode(member.getPassword()));

		Long id = memberRepository.save(member).getId();
		return id;
	}

	@Transactional(readOnly = true)
	public boolean checkUserIdDuplication(String userId) {
		boolean userIdDuplicate = memberRepository.existsByUserId(userId);
		return userIdDuplicate;
	}

	@Transactional(readOnly = true)
	public boolean checkNicknameDuplication(String nickname) {
		boolean nicknameDuplicate = memberRepository.existsByNickname(nickname);
		return nicknameDuplicate;
	}
}
