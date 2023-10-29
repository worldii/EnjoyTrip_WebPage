package com.ssafy.enjoytrip.config;


import com.ssafy.enjoytrip.core.media.service.ImageUploader;
import com.ssafy.enjoytrip.fake.MockImageUploader;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class UploadConfig {

    @Bean
    public ImageUploader imageUploader() {
        return new MockImageUploader();
    }
}
