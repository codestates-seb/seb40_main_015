package com.dongnebook.domain.member.ui;

import com.dongnebook.domain.book.application.BookService;
import com.dongnebook.domain.book.dto.response.BookSimpleResponse;
import com.dongnebook.domain.member.application.MemberService;

import com.dongnebook.domain.member.dto.request.MemberEditRequest;
import com.dongnebook.domain.member.dto.request.MemberRegisterRequest;
import com.dongnebook.domain.member.dto.request.MerchantSearchRequest;
import com.dongnebook.domain.member.dto.response.MemberDetailResponse;
import com.dongnebook.domain.member.dto.response.MemberExistsCheckResponse;
import com.dongnebook.domain.member.dto.response.MemberResponse;
import com.dongnebook.domain.member.dto.response.MerchantSectorCountResponse;

import com.dongnebook.global.Login;
import com.dongnebook.global.config.security.auth.userdetails.AuthMember;
import com.dongnebook.global.dto.request.PageRequest;

import org.springframework.data.domain.SliceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final BookService bookService;

    @PostMapping("/auth/signup")
    public ResponseEntity<Map<String, Long>> create(@Valid @RequestBody MemberRegisterRequest memberRegisterRequest) {
        Long createdMemberId = memberService.create(memberRegisterRequest);

        Map<String, Long> createdResult = new HashMap<>();
        createdResult.put("id", createdMemberId);

        URI createdMemberUri = ServletUriComponentsBuilder.fromPath("/member").path("/{id}").buildAndExpand(createdMemberId).toUri();

        return ResponseEntity.created(createdMemberUri).body(createdResult);
    } // /members/{id} 자원 생성

    @GetMapping("/auth/signup/checkId") //API로 중복체크하는 로직
    public ResponseEntity<MemberExistsCheckResponse> checkSameUserId(@RequestParam String id) {
        boolean userIdExists = memberService.checkUserIdDuplication(id);

        if (userIdExists){
            MemberExistsCheckResponse memberExistsCheckResponse = MemberExistsCheckResponse.builder()
                    .success(false)
                    .message("중복된 ID입니다. 다른 ID를 입력해 주세요.")
                    .build();
            return ResponseEntity.ok(memberExistsCheckResponse);
        }
        MemberExistsCheckResponse memberExistsCheckResponse = MemberExistsCheckResponse.builder()
                    .success(true)
                    .build();
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
            return ResponseEntity.ok(memberExistsCheckResponse);
        }
        MemberExistsCheckResponse memberExistsCheckResponse = MemberExistsCheckResponse.builder()
                .success(true)
                .build();
        return ResponseEntity.ok(memberExistsCheckResponse);
    }

    @PatchMapping("/member/edit")
    public void edit(@Login AuthMember member, @RequestBody MemberEditRequest memberEditRequest){

        memberService.edit(member.getMemberId(),memberEditRequest);

    }

    @GetMapping("/member/count")
    public ResponseEntity<ArrayList<MerchantSectorCountResponse>> getSectorMerchantCount(@ModelAttribute MerchantSearchRequest merchantSearchRequest){
        return ResponseEntity.ok(memberService.getSectorMerchantCounts(merchantSearchRequest));
    }

    @GetMapping("/member/sector")
    public ResponseEntity<SliceImpl<MemberResponse>> getLists(@ModelAttribute MerchantSearchRequest merchantSearchRequest,
        PageRequest pageRequest){
        return ResponseEntity.ok(memberService.getList(merchantSearchRequest,pageRequest));
    }


    @GetMapping("/member/{id}")
    public ResponseEntity<MemberDetailResponse> getMyInfo(@PathVariable Long id){
        return ResponseEntity.ok(memberService.getMemberInfo(id));
    }

    @GetMapping("/member/{id}/books")
    public SliceImpl<BookSimpleResponse> getMemberBooks(@PathVariable Long id, PageRequest pageRequest){
        return bookService.getListByMember(id,pageRequest);
    }

}
