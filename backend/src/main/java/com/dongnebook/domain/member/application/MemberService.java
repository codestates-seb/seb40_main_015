package com.dongnebook.domain.member.application;

import com.dongnebook.domain.member.dto.request.MemberEditRequest;
import com.dongnebook.domain.member.dto.request.MerchantSearchRequest;
import com.dongnebook.domain.member.dto.response.MemberDetailResponse;
import com.dongnebook.domain.member.dto.response.MemberResponse;
import com.dongnebook.domain.member.dto.response.MerchantSectorCountResponse;
import com.dongnebook.domain.member.repository.MemberQueryRepository;

import org.springframework.data.domain.SliceImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.member.dto.request.MemberRegisterRequest;
import com.dongnebook.domain.member.exception.MemberNotFoundException;
import com.dongnebook.domain.member.repository.MemberRepository;
import com.dongnebook.domain.model.Location;
import com.dongnebook.global.dto.request.PageRequest;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Getter
@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final MemberQueryRepository memberQueryRepository;

	@Transactional
	public Long create(MemberRegisterRequest memberRegisterRequest) {

		Member member = Member.builder()
			.userId(memberRegisterRequest.getUserId())
			.nickname(memberRegisterRequest.getNickname())
			.password(passwordEncoder.encode(memberRegisterRequest.getPassword()))
			.build();

		return memberRepository.save(member).getId();
	}

	@Transactional(readOnly = true)
	public boolean checkUserIdDuplication(String userId) {
		return memberRepository.existsByUserId(userId);
	}

	@Transactional(readOnly = true)
	public boolean checkNicknameDuplication(String nickname) {
		return memberRepository.existsByNickname(nickname);
	}

	@Transactional
	public void edit(Long memberId, MemberEditRequest memberEditRequest) {
		Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
		member.edit(memberEditRequest);
	}

	public ArrayList<MerchantSectorCountResponse> getSectorMerchantCounts(MerchantSearchRequest merchantSearchRequest) {

		List<Double> latRangeList =	Location.latRangeList(merchantSearchRequest.getLatitude());
		List<Double> lonRangeList = Location.lonRangeList(merchantSearchRequest.getLongitude());
		List<Location> sectorBookCounts = memberQueryRepository.getSectorMerchantCounts(merchantSearchRequest);
		ArrayList<MerchantSectorCountResponse> merchantSectorCountResponses = new ArrayList<>();

		for (int i = 0; i < 9; i++) {
			merchantSectorCountResponses.add(new MerchantSectorCountResponse());
		}

		for (Location location : sectorBookCounts) {

			Double latitude = location.getLatitude();
			Double longitude = location.getLongitude();
			int count = 0;
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					count++;
					if (latRangeList.get(i + 1) <= latitude && latitude <= latRangeList.get(i)
						&& lonRangeList.get(j) <= longitude && longitude <= lonRangeList.get(j + 1)) {
						makeMerchantCountResponse(merchantSectorCountResponses, count, location);
					}
				}
			}
		}
		return merchantSectorCountResponses;
	}

	private void makeMerchantCountResponse(ArrayList<MerchantSectorCountResponse> merchantSectorCountResponses,
		int index, Location location) {

		MerchantSectorCountResponse merchantSectorCountResponse = merchantSectorCountResponses.get(index-1);
		merchantSectorCountResponse.plusBookCount();

		if (Objects.isNull(merchantSectorCountResponse.getLocation())) {
			merchantSectorCountResponse.initLocation(location);
			merchantSectorCountResponse.initSector((long)index);
		}
	}

	public SliceImpl<MemberResponse> getList(MerchantSearchRequest merchantSearchRequest, PageRequest pageRequest) {
		return memberQueryRepository.getAll(merchantSearchRequest,pageRequest);
	}

	public MemberDetailResponse getMemberInfo(Long memberId) {
		return Optional.ofNullable(memberQueryRepository.getMyInfo(memberId))
			.orElseThrow(MemberNotFoundException::new);
	}
}

