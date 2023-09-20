package com.ssafy.enjoytrip.global.config;

import com.ssafy.enjoytrip.user.model.interceptor.JwtInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
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
        registry.addInterceptor(jwtInterceptor)
            .addPathPatterns("/**")
            .excludePathPatterns("/swagger-ui/**", "/swagger-resources/**", "/v2/api-docs",
                "/webjars/**", "/error/**", "/api/**", "/h2-console/**", "/favicon.ico");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        log.info("CORS Setting");
        registry.addMapping("/**")
            .allowedOrigins("*")
            .maxAge(1800);
    }

}
