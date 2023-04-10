package com.dongnebook.global.replication;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "spring.datasource.replication")
public class CustomDataSourceProperties {
	private String driverClassName;
	private String username;
	private String password;
	private String url;
	private final Map<String, Slave> slaves = new HashMap<>();

	@Getter
	@Setter
	public static class Slave{
		private String name;
		private String url;
	}
}
