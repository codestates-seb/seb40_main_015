package com.dongnebook.global.config.security.auth;

import java.util.Arrays;

import com.dongnebook.global.config.security.auth.filter.JwtAuthenticationFilter;
import com.dongnebook.global.config.security.auth.filter.JwtVerificationFilter;
import com.dongnebook.global.config.security.auth.handler.MemberAccessDeniedHandler;
import com.dongnebook.global.config.security.auth.handler.MemberAuthenticationEntryPoint;
import com.dongnebook.global.config.security.auth.handler.MemberAuthenticationFailureHandler;
import com.dongnebook.global.config.security.auth.handler.MemberAuthenticationSuccessHandler;
import com.dongnebook.global.config.security.auth.jwtTokenizer.JwtTokenizer;
import com.dongnebook.global.utils.CustomAuthorityUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfiguration {
	private final JwtTokenizer jwtTokenizer;
	private final CustomAuthorityUtils authorityUtils;

	public SecurityConfiguration(JwtTokenizer jwtTokenizer, CustomAuthorityUtils authorityUtils) {
		this.jwtTokenizer = jwtTokenizer;
		this.authorityUtils = authorityUtils;
	}

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
						.anyRequest().permitAll()
				);
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
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

	public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
		@Override
		public void configure(HttpSecurity builder) throws Exception {
			AuthenticationManager authenticationManager
					= builder.getSharedObject(AuthenticationManager.class);
			JwtAuthenticationFilter jwtAuthenticationFilter
					= new JwtAuthenticationFilter(authenticationManager, jwtTokenizer);
			jwtAuthenticationFilter.setFilterProcessesUrl("/auth/login"); // Login url
			jwtAuthenticationFilter.setAuthenticationSuccessHandler(new MemberAuthenticationSuccessHandler());
			jwtAuthenticationFilter.setAuthenticationFailureHandler(new MemberAuthenticationFailureHandler());

			JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer, authorityUtils);

			builder
					.addFilter(jwtAuthenticationFilter)
					.addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);
		}
	}
}

