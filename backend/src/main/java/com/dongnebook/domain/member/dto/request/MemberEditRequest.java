package com.dongnebook.domain.member.dto.request;

import com.dongnebook.domain.model.Location;

import lombok.Getter;

@Getter
public class MemberEditRequest {

	private String nickname;
	private Location location;
	private String avatarUrl;

	public MemberEditRequest(String nickname, Location location, String avatarUrl) {
		this.nickname = nickname;
		this.location = location;
		this.avatarUrl = avatarUrl;
	}


}
