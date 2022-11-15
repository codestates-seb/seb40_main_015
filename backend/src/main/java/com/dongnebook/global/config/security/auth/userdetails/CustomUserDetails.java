package com.dongnebook.global.config.security.auth.userdetails;

import com.dongnebook.domain.member.domain.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Getter
public class CustomUserDetails implements UserDetails {
    private Member member;
    private String id;
    private String userId;
    private String nickname;
    private String password;
    private Collection<GrantedAuthority> authorities;

    public CustomUserDetails(Member member){
        this.member = member;
    }

    public Member getMember() { return member; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(()->member.getRole());
        return authorities;
    }

    /*PK값*/
    @Override
    public String getUsername() { return id;}

    @Override
    public String getPassword() { return password; }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}


