package org.example.customauth;

import org.example.customauth.configuration.CustomUserDetailService;
import org.example.customauth.configuration.CustomUserDetails;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Collection;

@SpringBootApplication
public class CustomAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomAuthApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	@Bean
	CustomUserDetailService  getUserDetailService() {

		Collection<UserDetails> us = Arrays.asList(
				new CustomUserDetails("eddie","eddie",true,
				"USER","ADMIN"));

		return new CustomUserDetailService(us);


	}

}

