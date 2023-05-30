package com.ssafy.enjoytrip.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import com.ssafy.enjoytrip.jwt.model.interceptor.JwtInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Slf4j
@Configuration
@MapperScan(basePackages = "com.ssafy.enjoytrip.*.model.mapper")
public class WebMVCConfig implements WebMvcConfigurer {
    @Autowired
    JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("Interceptor Setting");
        // ToDo: 인증 서비스 URL 추가
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/user/info/*");
    }
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        log.info("CORS Setting");
        registry.addMapping("/**")
                .allowedOrigins("*")
                .maxAge(1800);
    }

}
