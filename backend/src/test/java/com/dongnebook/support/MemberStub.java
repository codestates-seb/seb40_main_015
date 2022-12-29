package com.dongnebook.support;

import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.member.dto.request.MemberEditRequest;
import com.dongnebook.domain.model.Location;

public enum MemberStub {

	MEMBER1(1L,"이성준","thwn40",LocationStub.봉천역.of(),"관악구 봉천동","www.naver.com"),
	MEMBER2(2L,"이성준2","thwn41",LocationStub.복마니정육점.of(),"관악구 봉천동","www.naver.com"),
	MEMBER3(3L,"이성준3","thwn42",LocationStub.삼성전자봉천역점.of(),"관악구 봉천동","www.naver.com"),
	MEMBER4(4L,"이성준4","thwn43",LocationStub.다이소봉천점.of(),"관악구 봉천동","www.naver.com"),
	MEMBER5(5L,"이성준5","thwn44",LocationStub.목포부부아구찜.of(),"관악구 봉천동","www.naver.com"),
	MEMBER6(6L,"이성준6","thwn45",LocationStub.스타벅스봉천점.of(),"관악구 봉천동","www.naver.com"),
	MEMBER7(7L,"이성준7","thwn46",LocationStub.농협은행봉천점.of(),"관악구 봉천동","www.naver.com");


	private final Long id;
	private final String userId;
	private final String nickname;
	private final Location location;
	private final String address;
	private final String avatarUrl;

	MemberStub(Long id, String userId, String nickname, Location location, String address,
		String avatarUrl) {
		this.id = id;
		this.userId = userId;
		this.nickname = nickname;
		this.location = location;
		this.address = address;
		this.avatarUrl = avatarUrl;
	}

	public Member of(Long memberId){

		Member member = Member.builder()
			.id(memberId)
			.userId(this.userId)
			.password("password")
			.avatarUrl(this.avatarUrl)
			.nickname(this.nickname)
			.build();

		member.edit(MemberEditRequest.builder()
				.location(this.location)
				.address(this.address)
			.build());
		return member;
	}
	public Member of(){

		Member member = Member.builder()
			.id(null)
			.userId(this.userId)
			.password("password")
			.avatarUrl(this.avatarUrl)
			.nickname(this.nickname)
			.build();

		member.edit(MemberEditRequest.builder()
			.location(this.location)
			.address(this.address)
			.build());
		return member;
	}

}
