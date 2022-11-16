package com.dongnebook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@EnableJpaAuditing
public class DongnebookApplication {

	public static void main(String[] args) {
		SpringApplication.run(DongnebookApplication.class, args);
	}

}
