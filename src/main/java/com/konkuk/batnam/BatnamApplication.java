package com.konkuk.batnam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BatnamApplication {
	public static void main(String[] args) {
		SpringApplication.run(BatnamApplication.class, args);
	}

}
