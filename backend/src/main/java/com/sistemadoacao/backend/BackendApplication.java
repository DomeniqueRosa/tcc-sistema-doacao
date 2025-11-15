package com.sistemadoacao.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

	// @Bean
	// public PasswordEncoder passwordEncoder() {
	// 	return new BCryptPasswordEncoder();
	// }

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
		System.out.println("deu certo");
	}

}
