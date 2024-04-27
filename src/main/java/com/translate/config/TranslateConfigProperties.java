package com.translate.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

// Add configuration-processor dependency
// Make Record or Class
// Add Configuration in properties file
// Chill ✨
@ConfigurationProperties(prefix = "rapid-api")
public record TranslateConfigProperties(String api) {}
