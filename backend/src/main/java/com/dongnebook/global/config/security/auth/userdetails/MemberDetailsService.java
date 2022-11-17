package com.dongnebook.global.config.security.auth.userdetails;

import com.dongnebook.domain.member.domain.Member;
import com.dongnebook.domain.member.exception.MemberNotFoundException;
import com.dongnebook.domain.member.repository.MemberRepository;
import com.dongnebook.global.utils.CustomAuthorityUtils;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class MemberDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final CustomAuthorityUtils authorityUtils;

    public MemberDetailsService(MemberRepository memberRepository, CustomAuthorityUtils authorityUtils) {
        this.memberRepository = memberRepository;
        this.authorityUtils = authorityUtils;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Member findMember = memberRepository.findByUserId(userId).orElseThrow(MemberNotFoundException::new);
        log.info(findMember.toString());
        CustomUserDetails customUserDetails = new CustomUserDetails(findMember);
        return customUserDetails;
    }

    //데이터베이스에서 조회한 회원 정보를 Spring Security의 User 정보로 변환하는 과정과 User의 권한 정보를 생성하는 과정을 캡슐화

    private final class CustomUserDetails extends Member implements UserDetails {

        @Override
        public String toString() {
            return "CustomUserDetails{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                ", nickname='" + nickname + '\'' +
                '}';
        }

        private Long memberId;
        private String userId;
        private String password;
        private List<String> roles;
        private String nickname;

        public CustomUserDetails(Member member) {
            this.memberId = member.getId();
            this.userId = member.getUserId();
            this.password = member.getPassword();
            this.roles = member.getRoles();
            this.nickname = member.getNickname();

        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return authorityUtils.createAuthorities(this.roles);
        }

        @Override
        public String getUsername() { return getUsername();}

        @Override
        public boolean isAccountNonExpired() { return true; }

        @Override
        public boolean isAccountNonLocked() { return true; }

        @Override
        public boolean isCredentialsNonExpired() { return true; }

        @Override
        public boolean isEnabled() { return true; }

        @Override
        public String getPassword() {
            return password;
        }
    }

}
