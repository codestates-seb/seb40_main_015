package com.dongnebook.domain.member.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.dongnebook.domain.member.dto.Request.MemberRegisterRequest;
import com.dongnebook.domain.model.Location;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "member")
@NoArgsConstructor
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "memberId", updatable = false)
	private Long memberId;

	@Column(name = "id", nullable = false, unique = true)
	private String id;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "name", nullable = false)
	private String name;

	@Embedded
	private Location location;

	@Column(name = "avatarUrl")
	private String avatarUrl;

	@Column(name = "AvgGrade")
	private Long AvgGrade;

	@Column(name = "totalBookCount")
	private Long totalBookCount;

	@Builder
	public Member(String id, String password, String name, Location location, String avatarUrl, Long avgGrade,
		Long totalBookCount) {
		this.id = id;
		this.password = password;
		this.name = name;
		this.avatarUrl = avatarUrl;
		this.location = location;
		this.AvgGrade = avgGrade;
		this.totalBookCount = totalBookCount;
	}

	public static Member create(MemberRegisterRequest memberRegisterRequest) {
		return Member.builder()
			.id(memberRegisterRequest.getId())
			.name(memberRegisterRequest.getName())
			.password(memberRegisterRequest.getPassword())
			.build();
	}
}