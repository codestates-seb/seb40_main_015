package com.dongnebook.domain.member.application;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.member.dto.request.MemberRegisterRequest;
import com.dongnebook.domain.member.exception.MemberNotFoundException;
import com.dongnebook.domain.member.repository.MemberRepository;
import com.dongnebook.domain.model.Location;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Getter
@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	private PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

	@Transactional
	public Long create(MemberRegisterRequest memberRegisterRequest) {
		Member member = Member.builder()
					.userId(memberRegisterRequest.getUserId())
					.nickname(memberRegisterRequest.getNickname())
					.password(passwordEncoder.encode(memberRegisterRequest.getPassword()))
					.build();
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

	@Transactional
	public void edit(Long memberId,Location location) {
		Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
		member.changeLocation(location);
	}
}
