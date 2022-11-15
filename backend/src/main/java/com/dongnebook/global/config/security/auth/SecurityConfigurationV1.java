package com.dongnebook.global.config.security.auth;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfigurationV1 {

//	private final JwtTokenizer jwtTokenizer;
//	private final CustomAuthorityUtils authorityUtils;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.headers().frameOptions().sameOrigin()
			.and()
			.csrf().disable()
			.cors().configurationSource(corsConfigurationSource())
			.and()
			.formLogin().disable()
			.httpBasic().disable()
			.authorizeHttpRequests(authorize -> authorize
				.anyRequest().permitAll()
			);
		return http.build();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "DELETE"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}

//public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
//	@Override
//	public void configure(HttpSecurity builder) throws Exception {
//		AuthenticationManager authenticationManager
//				= builder.getSharedObject(AuthenticationManager.class);
//		JwtAuthenticationFilter jwtAuthenticationFilter
//				= new JwtAuthenticationFilter(authenticationManager, jwtTokenizer);
//		jwtAuthenticationFilter.setFilterProcessesUrl("/auth/login"); // Login url
//		jwtAuthenticationFilter.setAuthenticationSuccessHandler(new MemberAuthenticationSuccessHandler());
//		jwtAuthenticationFilter.setAuthenticationFailureHandler(new MemberAuthenticationFailureHandler());
//
//		JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer, authorityUtils);
//
//		builder
//				.addFilter(jwtAuthenticationFilter)
//				.addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);
//	}
//}