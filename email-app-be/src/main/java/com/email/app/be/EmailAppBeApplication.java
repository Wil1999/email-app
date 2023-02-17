package com.email.app.be;

import java.util.Arrays;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
public class EmailAppBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailAppBeApplication.class, args);
	}
	
	
	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(false);
		//corsConfiguration.setAllowedOrigins(Arrays.asList("https://emailappfe.herokuapp.com/"));
		corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200/","https://emailappfe.herokuapp.com/"));
		corsConfiguration.setAllowedHeaders(Arrays.asList("Content-Type"));
		corsConfiguration.setExposedHeaders(Arrays.asList("Content-Type"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}
}
