package com.translate.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 1st Approach
@Configuration
public class WebConfig implements WebMvcConfigurer {

     @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://translator-web-app-nine.vercel.app/") // Add the origin(s) that should be allowed
                .allowedMethods("GET", "POST", "PUT", "DELETE"); // Add the HTTP methods that should be allowed
    }
}
