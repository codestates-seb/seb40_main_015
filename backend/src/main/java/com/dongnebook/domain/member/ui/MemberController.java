package com.dongnebook.domain.member.ui;

import com.dongnebook.domain.member.application.MemberService;

import com.dongnebook.domain.member.dto.request.MemberRegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
    @PostMapping("/signup")
    public ResponseEntity<Long> create(@Valid @RequestBody MemberRegisterRequest memberRegisterRequest){
        Long createdMemberId = memberService.create(memberRegisterRequest);
        URI createdMemberUri =
        return ResponseEntity.created(URI.create())
    } // /members/{id} 자원 생성
}
