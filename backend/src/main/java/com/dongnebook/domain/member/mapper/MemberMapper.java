package com.dongnebook.domain.member.mapper;

import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.member.dto.Request.MemberRegisterRequest;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {
    public Member memberRegisterRequestToMember(MemberRegisterRequest memberRegisterRequest){
        Member member = new Member();

        member.setUserId(memberRegisterRequest.getUserId());
        member.setNickname(memberRegisterRequest.getNickname());
        member.setPassword(memberRegisterRequest.getPassword());

        return member;
    }
}
