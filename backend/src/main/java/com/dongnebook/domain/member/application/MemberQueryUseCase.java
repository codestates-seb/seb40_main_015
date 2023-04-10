package com.dongnebook.domain.member.application;

import com.dongnebook.domain.member.domain.Member;

public interface MemberQueryUseCase {
	Member getMember(Long memberId);
}
