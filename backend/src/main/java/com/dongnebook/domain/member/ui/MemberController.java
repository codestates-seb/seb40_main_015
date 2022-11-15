package com.dongnebook.domain.member.ui;

import com.dongnebook.domain.member.application.MemberService;

import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.member.dto.request.MemberRegisterRequest;
import com.dongnebook.domain.member.dto.response.MemberIdResponse;
import lombok.AllArgsConstructor;
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

    @PostMapping("/signup")
    public ResponseEntity<MemberIdResponse> create(@Valid @RequestBody MemberRegisterRequest memberRegisterRequest) {
        Long createdMemberId = memberService.create(memberRegisterRequest);

        MemberIdResponse memberIdResponse = new MemberIdResponse(createdMemberId);

        URI createdMemberUri = ServletUriComponentsBuilder.fromPath("/member").path("/{id}").buildAndExpand(createdMemberId).toUri();
        return ResponseEntity.created(createdMemberUri).body(memberIdResponse);
    } // /members/{id} 자원 생성
}
