package com.dongnebook.domain.member.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.ResponseCookie;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;

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
import com.dongnebook.domain.member.exception.MemberHasOnLoanException;
import com.dongnebook.domain.member.repository.MemberQueryRepository;
import com.dongnebook.domain.member.repository.MemberRepository;
import com.dongnebook.domain.model.Location;
import com.dongnebook.domain.refreshtoken.domain.RefreshToken;
import com.dongnebook.domain.refreshtoken.repository.RefreshTokenRepository;
import com.dongnebook.global.dto.TokenDto;
import com.dongnebook.global.dto.request.PageRequest;
import com.dongnebook.global.security.auth.filter.TokenProvider;
import com.dongnebook.support.BookStub;
import com.dongnebook.support.LocationStub;
import com.dongnebook.support.MemberStub;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
	@Mock
	MemberRepository memberRepository;
	@Mock
	PasswordEncoder passwordEncoder;
	@Mock
	MemberQueryRepository memberQueryRepository;
	@Mock
	BookQueryRepository bookQueryRepository;
	@Mock
	RefreshTokenRepository refreshTokenRepository;
	@Mock
	TokenProvider tokenProvider;
	@Mock
	EntityManager em;

	@Value("${jwt.access-token-expiration-minutes}")
	private int accessTokenExpireTime;
	@Value("${jwt.refresh-token-expiration-minutes}")
	private int refreshTokenExpireTime;
	@Value("${jwt.secret-key}")
	private String secretKey;

	@InjectMocks
	MemberService memberService;

	@Test
	@DisplayName("회원가입을 한다.")
	void create() {

		// given
		Member member1 = MemberStub.MEMBER1.of(1L);
		MemberRegisterRequest request = MemberRegisterRequest.builder().nickname(member1.getNickname())
			.password("asdf123")
			.userId(member1.getUserId())
			.build();
		given(memberRepository.save(any(Member.class))).willReturn(member1);
		given(passwordEncoder.encode("asdf123")).willReturn("secretPassword");
		// when
		// then
		assertAll(
			() -> assertThat(memberService.create(request)).isEqualTo(1L),
			() -> verify(passwordEncoder).encode("asdf123"));

	}

	@Test
	@DisplayName("아이디가 중복이 아니면 false를 반환한다.")
	void checkUserIdDuplication_false() {
		// given
		String userId = "thwn40";
		given(memberRepository.existsByUserId(userId)).willReturn(false);

		// when
		// then
		assertThat(memberService.checkUserIdDuplication(userId)).isFalse();
	}

	@Test
	@DisplayName("아이디가 중복이면 true를 반환한다.")
	void checkUserIdDuplication_true() {
		// given
		String userId = "thwn40";
		given(memberRepository.existsByUserId(userId)).willReturn(true);

		// when
		// then
		assertAll(() -> assertThat(memberService.checkUserIdDuplication(userId)).isTrue(),
			() -> verify(memberRepository).existsByUserId(userId));
	}

	@Test
	@DisplayName("닉네임이 중복이 아니면 false를 반환한다.")
	void checkNicknameDuplication() {
		// given
		String nickName = "이성준";
		given(memberRepository.existsByNickname(nickName)).willReturn(false);

		// when
		// then

		assertAll(() -> assertThat(memberService.checkNicknameDuplication(nickName)).isFalse(),
			() -> verify(memberRepository).existsByNickname(nickName));
	}

	@Test
	@DisplayName("닉네임이 중복이면 True를 반환한다.")
	void checkNicknameDuplication_false() {
		// given
		String nickName = "이성준";
		given(memberRepository.existsByNickname(nickName)).willReturn(true);

		// when
		// then
		assertAll(() -> assertThat(memberService.checkNicknameDuplication(nickName)).isTrue(),
			() -> verify(memberRepository).existsByNickname(nickName));

	}

	@Test
	@DisplayName("대여중인책이 없을때만 회원 정보를 변경한다.")
	void edit() {
		//given
		Long memberId = 1L;
		Member member1 = MemberStub.MEMBER1.of(memberId);
		Location location1 = LocationStub.봉천역.of();
		given(memberQueryRepository.findByMemberWithRental(memberId)).willReturn(Optional.of(member1));
		MemberEditRequest request = MemberEditRequest.builder().location(location1)
			.address("관악구 봉천동")
			.avatarUrl("www.naver.com")
			.nickname("이성준2")
			.build();

		memberService.edit(memberId, request);
		assertAll(
			() -> assertThat(member1.getNickname()).isEqualTo("이성준2"),
			() -> verify(bookQueryRepository).updateBookLocation(member1, request.getLocation()));
	}

	@Test
	@DisplayName("대여중인책이 있으면 회원 정보를 변경할 수 없다.")
	void edit_if_has_rental() {
		//given
		Long memberId = 1L;
		Member member1 = MemberStub.MEMBER1.of(memberId);
		given(memberQueryRepository.findByMemberWithRental(memberId)).willReturn(Optional.of(member1));
		MemberEditRequest request = MemberEditRequest.builder().location(LocationStub.봉천역.of())
			.address("관악구 봉천동")
			.avatarUrl("www.naver.com")
			.nickname("이성준2")
			.build();

		Book book8 = BookStub.BOOK8.of().changeBookStateFromTo(BookState.RENTABLE, BookState.UNRENTABLE_RESERVABLE);
		member1.getBookList().add(book8);
		//when then
		assertThrows(MemberHasOnLoanException.class, () -> memberService.edit(memberId, request));
		assertAll(
			() -> assertThat(member1.getNickname()).isEqualTo("이성준"),
			() -> verify(bookQueryRepository, times(0)).updateBookLocation(member1, request.getLocation()));
	}

	@Test
	@DisplayName("리프레시토큰이 유효하면 새 액세스토큰을 발급한다.")
	void reissue() {
		//given
		Long memberId = 1L;
		String refreshToken = "refreshToken";
		String accessToken = "accessToken";
		RefreshToken savedRefreshToken = RefreshToken.builder().value(refreshToken)
			.id(memberId).build();
		given(refreshTokenRepository.findByValue(refreshToken)).willReturn(Optional.of(savedRefreshToken));
		given(memberRepository.findById(memberId)).willReturn(Optional.of(MemberStub.MEMBER1.of(1L)));
		given(tokenProvider.generateTokenDto(memberId)).willReturn(TokenDto.builder()
			.grantType("bearer")
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build());
		MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();

		//when
		memberService.reissue(refreshToken, mockHttpServletResponse);

		//then
		assertAll(
			() -> verify(memberRepository).findById(memberId),
			() -> verify(tokenProvider).generateTokenDto(memberId),
			() -> verify(refreshTokenRepository).findByValue(refreshToken),
			() -> assertThat(mockHttpServletResponse.getHeader("Authorization")).isEqualTo("Bearer " + accessToken),
			() -> assertThat(mockHttpServletResponse.getCookie(refreshToken).getValue()).isEqualTo(refreshToken));
	}

	@Test
	void logout() {
		String refreshToken = "refreshToken";
		Long memberId = 1L;
		MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();

		ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
			.maxAge(0)
			.path("/")
			.secure(true)
			.sameSite("None")
			.httpOnly(true)
			.build();
		mockHttpServletResponse.addHeader("Set-Cookie", cookie.toString());

		memberService.logout(refreshToken, mockHttpServletResponse);

		verify(refreshTokenRepository).deleteById(anyLong());

	}

	@Test
	@DisplayName("섹터당 회원 갯수를 가져온다.")
	void getSectorMerchantCounts() {
		Location location = LocationStub.봉천역.of();
		Integer width = 20;
		Integer height = 20;
		Integer sector = null;
		Integer level = 3;
		MerchantSearchRequest request = new MerchantSearchRequest(location.getLongitude(),
			location.getLatitude(), width, height, sector, level);
		given(memberQueryRepository.getSectorMerchantCounts(anyList(), anyList(),
			any(MerchantSearchRequest.class))).willReturn(List.of(location));

		List<MerchantSectorCountResponse> sectorMerchantCounts = memberService.getSectorMerchantCounts(request);
		assertAll(() -> assertThat(sectorMerchantCounts).hasSize(1),
			() -> assertThat(sectorMerchantCounts.get(0).getMerchantCount()).isOne(),
			() -> assertThat(sectorMerchantCounts.get(0).getSector()).isEqualTo(5),
			() -> verify(memberQueryRepository).getSectorMerchantCounts(anyList(), anyList(),
				any(MerchantSearchRequest.class)));

	}

	@Test
	@DisplayName("내 정보를 가져온다.")
	void getMemberInfo() {
		//given
		Long memberId = 1L;
		given(memberQueryRepository.findMyInfo(memberId)).willReturn(Optional.ofNullable(MemberDetailResponse.builder().build()));
		//when
		memberService.getMemberInfo(memberId);
		//then
		verify(memberQueryRepository).findMyInfo(memberId);
	}

	@Test
	@DisplayName("회원 한명을 가져온다.")
	void getById() {
		//given
		Long memberId = 1L;
		Member member1 = MemberStub.MEMBER1.of();
		given(memberRepository.findById(memberId)).willReturn(Optional.of(member1));
		//when
		//then
		assertThat(memberService.getById(memberId)).isEqualTo(member1);
	}

	@Test
	@DisplayName("섹터에 있는 회원의 갯수를 가져온다.")
	void getList() {
		PageRequest pageRequest = new PageRequest(null);
		Location location = LocationStub.봉천역.of();
		Integer width = 20;
		Integer height = 20;
		Integer sector = 5;
		Integer level = 3;
		Long memberId =1L;
		Member member1 = MemberStub.MEMBER1.of(memberId);
		MerchantSearchRequest request = new MerchantSearchRequest(location.getLongitude(),
			location.getLatitude(), width, height, sector, level);
		given(memberQueryRepository.getAll(any(),any(),any(),any())).willReturn(new SliceImpl<>(List.of(
			MemberResponse.builder().merchantName(member1.getNickname()).location(location)
				.merchantId(member1.getId()).build())));
		SliceImpl<MemberResponse> list = memberService.getList(request, pageRequest);
		assertThat(list.hasNext()).isFalse();
		assertThat(list.isLast()).isTrue();
		assertThat(list.getContent()).hasSize(1);

	}

}