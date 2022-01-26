package com.example.ex2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.example")
@EnableJpaRepositories("com.example")

public class Ex2Application {

	public static void main(String[] args) {
		SpringApplication.run(Ex2Application.class, args);
	}

}
