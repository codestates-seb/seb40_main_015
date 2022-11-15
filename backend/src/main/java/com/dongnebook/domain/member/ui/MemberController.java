package com.dongnebook.domain.member.ui;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.member.repository.MemberRepository;
import com.dongnebook.domain.model.Location;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
	private final MemberRepository memberRepository;
	@PostMapping
	public void SimpleSignUp(){
		Member build = Member.builder().avatarUrl("ㄴㅇㄹㅁㄴㅇㄹ")
			.avgGrade(5L)
			.location(Location.builder().
				latitude("37")
				.longitude("15")
				.build())
			.nickname("이성준")
			.password("asdf123132")
			.userId("thwn40")
			.build();

		memberRepository.save(build);
	}
}
