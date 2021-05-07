package com.abernathyclinic.mediscreen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableFeignClients("com.abernathyclinic.mediscreen.service")
public class MediscreenApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(MediscreenApplication.class, args);
	}

}
