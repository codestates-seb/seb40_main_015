package com.dongnebook.domain.member.ui;

import com.dongnebook.domain.member.application.MemberService;

import com.dongnebook.domain.member.dto.request.MemberRegisterRequest;
import com.dongnebook.domain.member.dto.response.MemberExistsCheckResponse;
import com.dongnebook.domain.member.dto.response.MemberIdResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/auth/signup")
    public ResponseEntity<MemberIdResponse> create(@Valid @RequestBody MemberRegisterRequest memberRegisterRequest) {
        Long createdMemberId = memberService.create(memberRegisterRequest);

        MemberIdResponse memberIdResponse = new MemberIdResponse(createdMemberId);

        URI createdMemberUri = ServletUriComponentsBuilder.fromPath("/member").path("/{id}").buildAndExpand(createdMemberId).toUri();
        return ResponseEntity.created(createdMemberUri).body(memberIdResponse);
    } // /members/{id} 자원 생성

    @GetMapping("/auth/signup/checkId") //API로 중복체크하는 로직
    public ResponseEntity<MemberExistsCheckResponse> checkSameUserId(@RequestParam String id) {
        boolean userIdExists = memberService.checkUserIdDuplication(id);

        if (userIdExists){
            MemberExistsCheckResponse memberExistsCheckResponse = MemberExistsCheckResponse.builder()
                    .success(false)
                    .message("중복된 ID입니다. 다른 ID를 입력해 주세요.")
                    .build();
        }
        MemberExistsCheckResponse memberExistsCheckResponse = MemberExistsCheckResponse.builder()
                    .success(true)
                    .build();
        System.out.println(memberExistsCheckResponse);
        return ResponseEntity.ok(memberExistsCheckResponse);
    }

    @GetMapping("/auth/signup/checknickname")
    public ResponseEntity<MemberExistsCheckResponse> checkSameNickName(@RequestParam String nickname) {
        boolean nicknameExists = memberService.checkNicknameDuplication(nickname);

        if (nicknameExists){
            MemberExistsCheckResponse memberExistsCheckResponse = MemberExistsCheckResponse.builder()
                    .success(false)
                    .message("중복된 닉네임입니다. 다른 닉네임을 사용해주세요.")
                    .build();
        }
        MemberExistsCheckResponse memberExistsCheckResponse = MemberExistsCheckResponse.builder()
                .success(true)
                .build();
        return ResponseEntity.ok(memberExistsCheckResponse);
    }
}
