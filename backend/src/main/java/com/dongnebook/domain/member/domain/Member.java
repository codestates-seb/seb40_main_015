package com.dongnebook.domain.member.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.dongnebook.domain.book.domain.Book;
import com.dongnebook.domain.dibs.domain.Dibs;
import com.dongnebook.domain.member.dto.request.MemberEditRequest;
import com.dongnebook.domain.member.dto.request.MemberRegisterRequest;
import com.dongnebook.domain.member.exception.LocationNotCreatedYetException;
import com.dongnebook.domain.model.BaseTimeEntity;
import com.dongnebook.domain.model.Location;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity implements Serializable {
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

	private String address;

	@Column(name = "avatar_url")
	private String avatarUrl;

	@Column(name = "avg_grade")
	private Double avgGrade = 0.0;

	@Column(name = "received_review_count")
	private Long receivedReviewCount = 0L;

	@Enumerated(EnumType.STRING)
	private Authority authority;

	@OneToMany(mappedBy = "member",cascade = CascadeType.REMOVE)
	private List<Dibs> dibsList = new ArrayList<>();

	@OneToMany(mappedBy = "member",cascade = CascadeType.REMOVE)
	private List<Book> bookList = new ArrayList<>();

	public Member(Long id, String userId, String nickname, Location location, String address, String avatarUrl) {
		this.id = id;
		this.userId = userId;
		this.nickname = nickname;
		this.location = location;
		this.address = address;
		this.avatarUrl = avatarUrl;
	}

	@Builder
	public Member(Long id, String userId, String password, String nickname, String avatarUrl)  {
		this.id = id;
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

	public void setAvgGrade(Double avgGrade){
		this.avgGrade = avgGrade;
	}

	public void upReviewCount() {
		this.receivedReviewCount++;
	}

	public void setAvgGradeAndUpCount(Long grade) {
		Double pastAvgGrade = this.getAvgGrade();
		Double newAvgGrade = (pastAvgGrade*this.getReceivedReviewCount() + grade)/(this.getReceivedReviewCount() + 1);
		this.setAvgGrade(newAvgGrade);
		this.upReviewCount();
	}


	public Location hasLocation() {
		return Optional.ofNullable(location).orElseThrow(LocationNotCreatedYetException::new);
	}
}