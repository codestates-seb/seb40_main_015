package com.dongnebook.support;

import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.member.dto.request.MemberEditRequest;
import com.dongnebook.domain.model.Location;

public enum MemberStub {

	MEMBER1(1L,"thwn40","이성준",LocationStub.봉천역.of(),"관악구 봉천동","www.naver.com"),
	MEMBER2(2L,"thwn41","이성준2",LocationStub.복마니정육점.of(),"관악구 봉천동","www.naver.com"),
	MEMBER3(3L,"thwn42","이성준3",LocationStub.삼성전자봉천역점.of(),"관악구 봉천동","www.naver.com"),
	MEMBER4(4L,"thwn43","이성준4",LocationStub.다이소봉천점.of(),"관악구 봉천동","www.naver.com"),
	MEMBER5(5L,"thwn44","이성준5",LocationStub.목포부부아구찜.of(),"관악구 봉천동","www.naver.com"),
	MEMBER6(6L,"thwn45","이성준6",LocationStub.스타벅스봉천점.of(),"관악구 봉천동","www.naver.com"),
	MEMBER7(7L,"thwn46","이성준7",LocationStub.농협은행봉천점.of(),"관악구 봉천동","www.naver.com"),
	MEMBER8(8L,"thwn47","이성준7",LocationStub.농협은행봉천점.of(),"관악구 봉천동","www.naver.com");


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
