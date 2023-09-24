package com.ssafy.enjoytrip.user.config;

import com.ssafy.enjoytrip.user.model.interceptor.BCryptPasswordEncoder;
import com.ssafy.enjoytrip.user.model.interceptor.PasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PasswordConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
