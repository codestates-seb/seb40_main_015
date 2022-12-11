package com.dongnebook.domain.member.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.SliceImpl;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.book.domain.BookState;
import com.dongnebook.domain.book.repository.BookQueryRepository;
import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.member.dto.request.MemberEditRequest;
import com.dongnebook.domain.member.dto.request.MemberRegisterRequest;
import com.dongnebook.domain.member.dto.request.MerchantSearchRequest;
import com.dongnebook.domain.member.dto.response.MemberDetailResponse;
import com.dongnebook.domain.member.dto.response.MemberResponse;
import com.dongnebook.domain.member.dto.response.MerchantSectorCountResponse;
import com.dongnebook.domain.member.exception.MemberNotFoundException;
import com.dongnebook.domain.member.repository.MemberQueryRepository;
import com.dongnebook.domain.member.repository.MemberRepository;
import com.dongnebook.domain.model.Location;
import com.dongnebook.domain.refreshtoken.domain.RefreshToken;
import com.dongnebook.domain.refreshtoken.exception.TokenInvalid;
import com.dongnebook.domain.refreshtoken.exception.TokenNotFound;
import com.dongnebook.domain.refreshtoken.repository.RefreshTokenRepository;
import com.dongnebook.global.dto.TokenDto;
import com.dongnebook.global.dto.request.PageRequest;
import com.dongnebook.global.error.exception.BusinessException;
import com.dongnebook.global.error.exception.ErrorCode;
import com.dongnebook.global.security.auth.filter.TokenProvider;
import com.dongnebook.global.security.auth.userdetails.AuthMember;

import io.jsonwebtoken.Claims;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final MemberQueryRepository memberQueryRepository;
	private final BookQueryRepository bookQueryRepository;
	private final RefreshTokenRepository refreshTokenRepository;
	private final TokenProvider tokenProvider;
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
		//어떤 회원의 대여중인책 -> 책 상태가 RENTABLE 이나 DELETE 가 아닌거
		Member member = memberQueryRepository.findByMemberWithRental(memberId)
			.orElseThrow(MemberNotFoundException::new);

		if (member.getBookList()
			.stream()
			.anyMatch(this::isBeingRental)) {
			throw new BusinessException("대여중인 책이 있어서 변경할 수 없습니다.", ErrorCode.MEMBER_HAS_BOOK_ON_LOAN);
		}

		member.edit(memberEditRequest);
		em.flush();
		bookQueryRepository.updateBookLocation(member, memberEditRequest.getLocation());
	}


	@Transactional
	public Long reissue(String refreshToken,
		HttpServletResponse response) {
		refreshToken = Optional.ofNullable(refreshToken)
			.orElseThrow(TokenNotFound::new);
		Claims claims = tokenProvider.parseClaims(refreshToken);
		Member member = getById(Long.parseLong(claims.getSubject()));
		AuthMember authMember = AuthMember.of(member);
		Long memberId = authMember.getMemberId();

		TokenDto tokenDto = tokenProvider.generateTokenDto(authMember);
		String newRTK = tokenDto.getRefreshToken();
		String newATK = tokenDto.getAccessToken();

		RefreshToken savedRefreshToken = refreshTokenRepository.findById(memberId)
			.orElseThrow(MemberNotFoundException::new);

		if (!savedRefreshToken.getValue().equals(refreshToken)) {
			throw new TokenInvalid();
		}

		RefreshToken newRefreshToken = savedRefreshToken.updateValue(newRTK);
		refreshTokenRepository.save(newRefreshToken);

		ResponseCookie cookie = ResponseCookie.from("refreshToken", newRTK)
			.maxAge(7 * 24 * 60 * 60)
			.path("/")
			.secure(true)
			.sameSite("None")
			.httpOnly(true)
			.build();

		response.setHeader("Set-Cookie", cookie.toString());
		response.setHeader("Authorization", "Bearer " + newATK);
		return member.getId();
	}

	// 로그아웃
	@Transactional
	public void logout(String refreshToken, HttpServletRequest request, HttpServletResponse response) {

		refreshToken = Optional.ofNullable(refreshToken)
			.orElseThrow(TokenNotFound::new);
		ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
			.maxAge(0)
			.path("/")
			.secure(true)
			.sameSite("None")
			.httpOnly(true)
			.build();
		response.setHeader("Set-Cookie", cookie.toString());

		refreshTokenRepository.deleteByKey(Long.valueOf(tokenProvider.parseClaims(refreshToken).getSubject()));
	}

	public ArrayList<MerchantSectorCountResponse> getSectorMerchantCounts(MerchantSearchRequest request) {

		List<Double> latRangeList = latRangeList(request);
		List<Double> lonRangeList = lonRangeList(request);
		List<Location> sectorBookCounts = memberQueryRepository.getSectorMerchantCounts(latRangeList,lonRangeList,request);
		ArrayList<MerchantSectorCountResponse> merchantSectorCountResponses = new ArrayList<>();
		HashMap<Integer, Integer> indexMap = new HashMap<>();
		int arrIndex = 0;

		for (Location location : sectorBookCounts) {
			arrIndex = addMerchantCountPerSector(request, latRangeList, lonRangeList, merchantSectorCountResponses, indexMap,
				arrIndex, location);
		}
		return merchantSectorCountResponses;
	}

	private boolean checkRange(List<Double> latRangeList, List<Double> lonRangeList, Double latitude, Double longitude,
		int i, int j) {
		return latRangeList.get(i + 1) <= latitude && latitude <= latRangeList.get(i)
			&& lonRangeList.get(j) <= longitude && longitude <= lonRangeList.get(j + 1);
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

	public Member getById(Long memberId) {
		return memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
	}

	public SliceImpl<MemberResponse> getList(MerchantSearchRequest merchantSearchRequest, PageRequest pageRequest) {
		return memberQueryRepository.getAll(latRangeList(merchantSearchRequest),lonRangeList(merchantSearchRequest),merchantSearchRequest, pageRequest);
	}

	private List<Double> lonRangeList(MerchantSearchRequest merchantSearchRequest) {
		return Location.lonRangeList(merchantSearchRequest.getLongitude(), merchantSearchRequest.getWidth(),
			merchantSearchRequest.getLevel());
	}

	private List<Double> latRangeList(MerchantSearchRequest merchantSearchRequest) {
		return Location.latRangeList(merchantSearchRequest.getLatitude(), merchantSearchRequest.getHeight(),
			merchantSearchRequest.getLevel());
	}

	private boolean isBeingRental(Book book) {
		return book.getBookState().equals(BookState.TRADING) || book.getBookState()
			.equals(BookState.UNRENTABLE_RESERVABLE) || book.getBookState().equals(BookState.UNRENTABLE_UNRESERVABLE);
	}

	private int addMerchantCountPerSector(MerchantSearchRequest request, List<Double> latRangeList, List<Double> lonRangeList,
		ArrayList<MerchantSectorCountResponse> merchantSectorCountResponses, HashMap<Integer, Integer> indexMap,
		int arrIndex, Location location) {
		int sector = 0;
		Loop:
		for (int i = 0; i < request.getLevel(); i++) {
			for (int j = 0; j < request.getLevel(); j++) {
				sector++;
				if (checkRange(latRangeList, lonRangeList, location.getLatitude(), location.getLongitude(), i, j)) {
					if (makeMerchantCountResponse(merchantSectorCountResponses, sector, arrIndex, location,
						indexMap)) {
						arrIndex += 1;
						break Loop;
					}
				}
			}
		}
		return arrIndex;
	}
}

