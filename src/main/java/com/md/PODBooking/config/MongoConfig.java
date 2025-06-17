package com.md.PODBooking.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import java.util.Optional;

@Configuration
@EnableMongoAuditing
public class MongoConfig {

    @Value("${audit.user}")
    private String user;

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.ofNullable(user);
    }
}
