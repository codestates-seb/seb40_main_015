package com.dongnebook.domain.member.domain;

import javax.persistence.*;
import javax.validation.constraints.Size;

import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.chat.domain.ChatRoom;
import com.dongnebook.domain.dibs.domain.Dibs;
import com.dongnebook.domain.member.dto.request.MemberEditRequest;
import com.dongnebook.domain.member.dto.request.MemberRegisterRequest;
import com.dongnebook.domain.model.BaseTimeEntity;
import com.dongnebook.domain.model.Location;


import lombok.*;

import java.util.ArrayList;
import java.util.List;


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

	@Column(name = "oauth_id", unique = true)
	private String oauthId;

	@Size(min = 8)
	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "nickname", nullable = false)
	private String nickname;

	@Embedded
	private Location location;

	private String address;

	@Column(name = "avatar_url")
	private String avatarUrl;

	@Column(name = "avg_grade")
	private Long avgGrade = 4L;

	@Enumerated(EnumType.STRING)
	private Authority authority;

	@OneToMany(mappedBy = "member",cascade = CascadeType.REMOVE)
	private List<Dibs> dibsList = new ArrayList<>();

	@OneToMany(mappedBy = "member")
	private List<Book> bookList = new ArrayList<>();



	@Builder
	public Member(String userId, String password, String nickname, String avatarUrl)  {
		this.userId = userId;
		this.password = password;
		this.nickname = nickname;
		this.authority = Authority.ROLE_USER;
		this.avatarUrl = avatarUrl;
	}

	public static Member create(MemberRegisterRequest memberRegisterRequest) {
		return Member.builder()
			.userId(memberRegisterRequest.getUserId())
			.nickname(memberRegisterRequest.getNickname())
			.password(memberRegisterRequest.getPassword())

			.build();
	}

	public void edit(MemberEditRequest memberEditRequest){
		this.avatarUrl = memberEditRequest.getAvatarUrl()==null ? this.avatarUrl : memberEditRequest.getAvatarUrl();
		this.location = memberEditRequest.getLocation()==null ? this.location : memberEditRequest.getLocation();
		this.nickname = memberEditRequest.getNickname()==null ? this.nickname : memberEditRequest.getNickname();
		this.address= memberEditRequest.getAddress()==null ? this.address : memberEditRequest.getAddress();
	}

	public Member oauthUpdate(String name, String email) {
		this.nickname = name;
		this.userId = email;
		return this;
	}

	public boolean hasSameId(Long id) {
		return this.id.equals(id);
	}

}