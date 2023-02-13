package com.baseApp.backend.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${front-end.url.base}")
    String frontEndPathBase;

    @Bean
    public String frontEndPathBase() {
        return frontEndPathBase;
    }
}
