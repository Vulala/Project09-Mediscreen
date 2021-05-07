package com.abernathyclinic.mediscreen;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Configuration class used to enable and define a Docket for the Swagger 2
 * documentations and to Override the {@link addCorsMappings} from the
 * {@link WebMvcConfigurer} to allow CRUD operations from the Front-end with the
 * CORS's header. <br>
 */
@Configuration
@EnableSwagger2
public class MediscreenConfiguration implements WebMvcConfigurer {

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

	/**
	 * Add the CORS header for the specified CRUD operations. <br>
	 * <i><blockquote> 'The header CORS « Access-Control-Allow-Origin » is missing'.
	 * </blockquote></i>
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE").allowedOrigins("*");
	}
}
