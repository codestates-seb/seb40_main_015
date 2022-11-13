package com.dongnebook.global.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class SecurityConfigurationV1 {
	public SecurutyFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.headers().frameOptions().sameOrigin()
			.and()
			.csrf().disable()
			.formLogin().disable()
			.httpBasic().disable()
			.authorizeHttpRequests(authorize -> authorize
				.anyRequest().permitAll()
			);
		return http.build();
	}
}
