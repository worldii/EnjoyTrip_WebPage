package com.ssafy.enjoytrip.config;

import com.ssafy.enjoytrip.fake.MockUploadService;
import com.ssafy.enjoytrip.media.service.UploadService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class UploadConfig {

    @Bean
    public UploadService uploadService() {
        return new MockUploadService();
    }
}
