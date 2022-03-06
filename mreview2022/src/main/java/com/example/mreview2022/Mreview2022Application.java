package com.example.mreview2022;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Mreview2022Application {

	public static void main(String[] args) {
		SpringApplication.run(Mreview2022Application.class, args);
	}

}
