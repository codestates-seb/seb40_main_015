package com.dongnebook.global.replication;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "spring.datasource.replication")
public class CustomDataSourceProperties {
	private String username;
	private String password;
	private Master master;
	private List<Slave> slaves;

	@Getter
	@Setter
	public static class Master{
		private String name;
		private String url;
	}

	@Getter
	@Setter
	public static class Slave{
		private String name;
		private String url;
	}
}
