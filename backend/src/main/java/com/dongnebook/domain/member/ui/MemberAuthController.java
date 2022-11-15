package com.dongnebook.domain.member.ui;

import com.dongnebook.domain.member.application.MemberService;
import com.dongnebook.domain.member.dto.Request.MemberRegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class MemberAuthController {

    private final MemberService memberService;
    @PostMapping("/signup")
    public ResponseEntity<Long> create(@Valid MemberRegisterRequest memberRegisterRequest){
        Long userId = memberService.create(memberRegisterRequest).;
        return
    }
}
