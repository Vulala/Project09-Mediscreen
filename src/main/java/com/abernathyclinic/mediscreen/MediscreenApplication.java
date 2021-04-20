package com.abernathyclinic.mediscreen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableFeignClients("com.abernathyclinic.mediscreen.service")
@EnableSwagger2
public class MediscreenApplication {

	public static void main(String[] args) {
		SpringApplication.run(MediscreenApplication.class, args);
	}

	/**
	 * Swagger configuration. <br>
	 * Ask to work for the controller package only, that's to disable the
	 * documentation of the default Spring Boot error controller. <br>
	 */
	@Bean
	public Docket swaggerConfigurationBean() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.abernathyclinic.mediscreen.controller"))
				.paths(PathSelectors.any()).build();
	}
}
