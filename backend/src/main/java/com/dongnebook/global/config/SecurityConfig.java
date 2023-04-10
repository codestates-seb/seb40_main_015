package com.dongnebook.global.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.dongnebook.domain.refreshtoken.repository.RefreshTokenRepository;
import com.dongnebook.global.security.auth.filter.JwtAuthenticationFilter;
import com.dongnebook.global.security.auth.filter.JwtVerificationFilter;
import com.dongnebook.global.security.auth.filter.TokenProvider;
import com.dongnebook.global.security.auth.handler.MemberAccessDeniedHandler;
import com.dongnebook.global.security.auth.handler.MemberAuthenticationEntryPoint;
import com.dongnebook.global.security.auth.handler.MemberAuthenticationFailureHandler;
import com.dongnebook.global.security.auth.handler.MemberAuthenticationSuccessHandler;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final RefreshTokenRepository refreshTokenRepository;
	private final TokenProvider tokenProvider;


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
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.exceptionHandling()
				.authenticationEntryPoint(new MemberAuthenticationEntryPoint())
				.accessDeniedHandler(new MemberAccessDeniedHandler())
				.and()
				.apply(new CustomFilterConfigurer())
				.and()
				.authorizeHttpRequests(authorize -> authorize
						.anyRequest().permitAll());
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(List.of("http://192.168.219.100:3000","http://localhost:3000","https://dongne-book.com"
		,"http://dongne-book.com","https://dongne-book-server.com","http://dongne-book-server.com"));
		configuration.setAllowCredentials(true);
		configuration.addExposedHeader("Authorization");
		configuration.addAllowedHeader("*");
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "DELETE"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	private class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {

		@Override
		public void configure(HttpSecurity builder) {
			AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);

			JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(tokenProvider, authenticationManager);
			jwtAuthenticationFilter.setFilterProcessesUrl("/auth/login");
			jwtAuthenticationFilter.setAuthenticationSuccessHandler(new MemberAuthenticationSuccessHandler(refreshTokenRepository));
			jwtAuthenticationFilter.setAuthenticationFailureHandler(new MemberAuthenticationFailureHandler());

			JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(tokenProvider);

			builder
					.addFilter(jwtAuthenticationFilter)
					.addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);
		}
	}
}