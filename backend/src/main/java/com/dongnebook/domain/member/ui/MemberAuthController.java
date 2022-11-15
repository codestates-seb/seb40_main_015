package com.dongnebook.domain.member.ui;

import com.dongnebook.domain.member.application.MemberService;
import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.member.dto.Request.MemberRegisterRequest;
import com.dongnebook.domain.member.mapper.MemberMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class MemberAuthController {
    private final MemberService memberService;
    private final MemberMapper mapper;

    public MemberAuthController(MemberService memberService, MemberMapper mapper) {
        this.memberService = memberService;
        this.mapper = mapper;
    }
    @PostMapping("/signup")
    public ResponseEntity<Long> create(@Valid @RequestBody MemberRegisterRequest memberRegisterRequest){
        Long createdMemberId = memberService.create(memberRegisterRequest);
        return ResponseEntity.created(URI.create())
    } // /members/{id} 자원 생성
}
