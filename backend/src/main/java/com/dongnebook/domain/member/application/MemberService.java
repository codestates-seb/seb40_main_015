package com.dongnebook.domain.member.application;

import com.dongnebook.domain.book.repository.BookQueryRepository;
import com.dongnebook.domain.member.dto.request.MerchantSearchRequest;

import com.dongnebook.domain.member.dto.request.MemberEditRequest;

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
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import java.util.Optional;

import javax.persistence.EntityManager;

@Slf4j
@Getter
@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final MemberQueryRepository memberQueryRepository;
	private final BookQueryRepository bookQueryRepository;
	private final EntityManager em;

	@Transactional
	public Long create(MemberRegisterRequest memberRegisterRequest) {

		Member member = Member.builder()
			.userId(memberRegisterRequest.getUserId())
			.nickname(memberRegisterRequest.getNickname())
			.password(passwordEncoder.encode(memberRegisterRequest.getPassword()))
			.avatarUrl("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png")
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
		em.flush();
		bookQueryRepository.updateBookLocation(member,memberEditRequest.getLocation());


	}

	public ArrayList<MerchantSectorCountResponse> getSectorMerchantCounts(MerchantSearchRequest request) {

		List<Double> latRangeList = Location.latRangeList(request.getLatitude(), request.getHeight(),
			request.getLevel());
		List<Double> lonRangeList = Location.lonRangeList(request.getLongitude(), request.getWidth(),
			request.getLevel());
		List<Location> sectorBookCounts = memberQueryRepository.getSectorMerchantCounts(request);
		ArrayList<MerchantSectorCountResponse> merchantSectorCountResponses = new ArrayList<>();
		HashMap<Integer, Integer> indexMap = new HashMap<>();
		int arrIndex = 0;

		for (Location location : sectorBookCounts) {
			Double latitude = location.getLatitude();
			Double longitude = location.getLongitude();
			int sector = 0;
			Loop :
			for (int i = 0; i < request.getLevel(); i++) {
				for (int j = 0; j < request.getLevel(); j++) {
					sector++;
					if (latRangeList.get(i + 1) <= latitude && latitude <= latRangeList.get(i)
						&& lonRangeList.get(j) <= longitude && longitude <= lonRangeList.get(j + 1)) {
						if (makeMerchantCountResponse(merchantSectorCountResponses, sector, arrIndex, location,
							indexMap)) {
							arrIndex += 1;
							break Loop;
						}
					}
				}
			}
		}
		return merchantSectorCountResponses;
	}

	private boolean makeMerchantCountResponse(ArrayList<MerchantSectorCountResponse> merchantSectorCountResponses,
		int sector, int arrIndex, Location location, HashMap<Integer, Integer> indexMap) {
		boolean newResponse = false;

		if (Optional.ofNullable(indexMap.get(sector)).isEmpty()) {
			merchantSectorCountResponses.add(new MerchantSectorCountResponse());
			indexMap.put(sector, arrIndex);
			newResponse = true;
		}

		MerchantSectorCountResponse merchantSectorCountResponse = merchantSectorCountResponses.get(
			indexMap.get(sector));
		merchantSectorCountResponse.plusBookCount();

		if (Objects.isNull(merchantSectorCountResponse.getLocation())) {
			merchantSectorCountResponse.initLocation(location);
			merchantSectorCountResponse.initSector(sector);
		}

		return newResponse;
	}

	public MemberDetailResponse getMemberInfo(Long memberId) {
		return Optional.ofNullable(memberQueryRepository.getMyInfo(memberId))
			.orElseThrow(MemberNotFoundException::new);
	}

	public Member findById(Long memberId){
		return memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
	}

	public SliceImpl<MemberResponse> getList(MerchantSearchRequest merchantSearchRequest, PageRequest pageRequest) {
		return memberQueryRepository.getAll(merchantSearchRequest, pageRequest);
	}

}

