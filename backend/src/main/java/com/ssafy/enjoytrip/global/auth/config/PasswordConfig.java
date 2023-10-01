package com.ssafy.enjoytrip.global.auth.config;

import com.ssafy.enjoytrip.infra.BCryptPasswordEncoder;
import com.ssafy.enjoytrip.infra.PasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PasswordConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
