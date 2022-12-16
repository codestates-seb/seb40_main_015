package com.dongnebook.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		//이 경로를 구독한사람에게 메시지를 전달
		config.enableSimpleBroker("/sub");
		//클라이언트가 서버에 메세지를 보낼때 붙여야하는 prefix
		config.setApplicationDestinationPrefixes("/pub");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/stomp/chat")
			.setAllowedOrigins("http://localhost:3000","https://dongne-book.com"
				,"http://dongne-book.com","https://dongne-book-server.com","http://dongne-book-server.com")
			.withSockJS();
	}
}
