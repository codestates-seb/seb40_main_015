package com.dongnebook.global.config;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.dongnebook.global.security.auth.handler.LoginResolver;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class WebConfig implements WebMvcConfigurer {

	private final LoginResolver loginResolver;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(loginResolver);
	}
}
