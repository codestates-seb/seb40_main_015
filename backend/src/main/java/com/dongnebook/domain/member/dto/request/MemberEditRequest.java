package com.dongnebook.domain.member.dto.request;

import com.dongnebook.domain.model.Location;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberEditRequest {
	private String nickname;
	private Location location;
	private String address;
	private String avatarUrl;

	@Builder
	public MemberEditRequest(String nickname, Location location, String address, String avatarUrl) {
		this.nickname = nickname;
		this.location = location;
		this.address = address;
		this.avatarUrl = avatarUrl;
	}


}
