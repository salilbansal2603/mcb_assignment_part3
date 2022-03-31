package com.nagarro.userLogin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication()
@EnableScheduling
public class UserLoginApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserLoginApplication.class, args);
	}


}
