package com.translate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import com.translate.config.TranslateConfigProperties;

@SpringBootApplication
@EnableConfigurationProperties(TranslateConfigProperties.class)
public class TranslateApplication {
	public static void main(String[] args) {
		SpringApplication.run(TranslateApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
