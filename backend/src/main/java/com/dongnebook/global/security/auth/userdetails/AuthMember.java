package com.dongnebook.global.security.auth.userdetails;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.model.Location;

import lombok.Getter;



@Getter
public class AuthMember extends Member implements UserDetails {
	private Long memberId;
	private String userId;
	private String password;
	private List<String> roles;
	private Location location;
	private String nickname;

	private AuthMember(Member member) {
		this.memberId = member.getId();
		this.userId = member.getUserId();
		this.password = member.getPassword();
		this.roles = List.of(member.getAuthority().toString());
		this.location = member.getLocation();
		this.nickname = member.getNickname();
	}

	private AuthMember(Long id, List<String> roles) {
		this.memberId = id;
		this.password = "";
		this.roles = roles;
	}

	public static AuthMember of(Member member) {
		return new AuthMember(member);
	}

	public static AuthMember of(Long id, List<String> roles) {
		return new AuthMember(id, roles);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(roles.get(0)));
	}

	@Override
	public String getUsername() {
		return userId;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
