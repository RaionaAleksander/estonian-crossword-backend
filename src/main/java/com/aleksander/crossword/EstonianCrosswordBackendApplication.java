package com.aleksander.crossword;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class EstonianCrosswordBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EstonianCrosswordBackendApplication.class, args);
	}

}
