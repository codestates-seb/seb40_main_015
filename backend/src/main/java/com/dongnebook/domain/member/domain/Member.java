package com.dongnebook.domain.member.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.dongnebook.domain.dibs.domain.Dibs;
import com.dongnebook.domain.member.dto.request.MemberRegisterRequest;
import com.dongnebook.domain.model.BaseTimeEntity;
import com.dongnebook.domain.model.Location;

import lombok.*;


@Entity
@Getter
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false)
	private Long id;

	@Column(name = "user_id", nullable = false, unique = true)
	private String userId;

	@Size(min = 8)
	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "nickname", nullable = false)
	private String nickname;

	@Embedded
	private Location location;

	@Column(name = "avatar_url")
	private String avatarUrl;

	@Column(name = "avg_grade")
	private Long avgGrade;

	@OneToMany(mappedBy = "member",cascade = CascadeType.REMOVE)
	private List<Dibs> dibsList = new ArrayList<>();

	@Builder
	public Member(String userId, String password, String nickname, Location location, String avatarUrl, Long avgGrade) {
		this.userId = userId;
		this.password = password;
		this.nickname = nickname;
		this.avatarUrl = avatarUrl;
		this.location = location;
		this.avgGrade = avgGrade;
	}

	public static Member create(MemberRegisterRequest memberRegisterRequest) {

		return Member.builder()
			.userId(memberRegisterRequest.getUserId())
			.nickname(memberRegisterRequest.getNickname())
			.password(memberRegisterRequest.getPassword())
			.build();
	}

	public void changeLocation(Location location){
		this.location = location;
	}


}