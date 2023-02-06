package com.dongnebook.domain.member.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.persistence.EntityManager;
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
import com.dongnebook.domain.member.dto.request.MerchantSearchableRequest;
import com.dongnebook.domain.member.dto.response.MemberDetailResponse;
import com.dongnebook.domain.member.dto.response.MemberResponse;
import com.dongnebook.domain.member.dto.response.MerchantSectorCountResponse;
import com.dongnebook.domain.member.exception.MemberHasOnLoanException;
import com.dongnebook.domain.member.exception.MemberNotFoundException;
import com.dongnebook.domain.member.repository.MemberQueryRepository;
import com.dongnebook.domain.member.repository.MemberRepository;
import com.dongnebook.domain.model.Location;
import com.dongnebook.domain.refreshtoken.domain.RefreshToken;
import com.dongnebook.domain.refreshtoken.exception.TokenNotFound;
import com.dongnebook.domain.refreshtoken.repository.RefreshTokenRepository;
import com.dongnebook.global.dto.TokenDto;
import com.dongnebook.global.dto.request.PageRequest;
import com.dongnebook.global.security.auth.filter.TokenProvider;

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
	List<Double> latRangeList;
	List<Double> lonRangeList;

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

		if (member.getBookList().stream().anyMatch(this::isBeingRental)) {
			throw new MemberHasOnLoanException();
		}

		member.edit(memberEditRequest);
		em.flush();
		bookQueryRepository.updateBookLocation(member, memberEditRequest.getLocation());
	}

	@Transactional
	public Long reissue(String refreshToken,
		HttpServletResponse response) {
		tokenProvider.validateToken(refreshToken);
		RefreshToken savedRefreshToken = refreshTokenRepository.findByValue(refreshToken)
			.orElseThrow(MemberNotFoundException::new);

		Member member = getById(savedRefreshToken.getMemberId());

		TokenDto tokenDto = tokenProvider.generateTokenDto(member.getId());
		String newRTK = tokenDto.getRefreshToken();
		String newATK = tokenDto.getAccessToken();

		RefreshToken newRefreshToken = savedRefreshToken.updateValue(newRTK);
		refreshTokenRepository.save(newRefreshToken);
		refreshTokenRepository.deleteByValue(refreshToken);

		ResponseCookie cookie = ResponseCookie.from("refreshToken", newRTK)
			.maxAge(7L * 24 * 60 * 60)
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
	public void logout(String refreshToken, HttpServletResponse response) {

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

		refreshTokenRepository.deleteById(tokenProvider.parseClaims(refreshToken));
	}

	public List<MerchantSectorCountResponse> getSectorMerchantCounts(MerchantSearchableRequest condition) {

		this.latRangeList = latRangeList(condition);
		this.lonRangeList = lonRangeList(condition);
		List<Location> sectorMerchantCounts = memberQueryRepository.getSectorMerchantCounts(latRangeList, lonRangeList,
			condition);

		Map<Integer, MerchantSectorCountResponse> collect = sectorMerchantCounts.stream()
			.flatMap(location ->
				IntStream.iterate(1, sector -> sector <= Math.pow(condition.getLevel(), 2), sector -> sector + 1)
					.filter(sector -> location.checkRange(condition,sector))
					.mapToObj(sector -> new MerchantSectorCountResponse(sector, location)))
			.collect(
				Collectors.toMap(MerchantSectorCountResponse::getSector,
					MerchantSectorCountResponse::increaseMerchantCount,
					(exist, newOne) -> exist.increaseMerchantCount()));

		return new ArrayList<>(collect.values());
	}

	public MemberDetailResponse getMemberInfo(Long memberId) {
		return memberQueryRepository.findMyInfo(memberId)
			.orElseThrow(MemberNotFoundException::new);
	}

	public Member getById(Long memberId) {
		return memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
	}

	public SliceImpl<MemberResponse> getList(MerchantSearchableRequest merchantSearchRequest, PageRequest pageRequest) {
		return memberQueryRepository.getAll(latRangeList(merchantSearchRequest), lonRangeList(merchantSearchRequest),
			merchantSearchRequest, pageRequest);
	}

	private List<Double> lonRangeList(MerchantSearchableRequest merchantSearchRequest) {
		return Location.lonRangeList(merchantSearchRequest.getLongitude(), merchantSearchRequest.getWidth(),
			merchantSearchRequest.getLevel());
	}

	private List<Double> latRangeList(MerchantSearchableRequest merchantSearchRequest) {
		return Location.latRangeList(merchantSearchRequest.getLatitude(), merchantSearchRequest.getHeight(),
			merchantSearchRequest.getLevel());
	}

	private boolean isBeingRental(Book book) {
		return book.getBookState().equals(BookState.TRADING) || book.getBookState()
			.equals(BookState.UNRENTABLE_RESERVABLE) || book.getBookState().equals(BookState.UNRENTABLE_UNRESERVABLE);
	}
}

