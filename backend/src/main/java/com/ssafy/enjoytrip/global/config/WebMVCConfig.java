package com.ssafy.enjoytrip.global.config;

import com.ssafy.enjoytrip.user.model.interceptor.JwtInterceptor;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
@MapperScan(basePackages = "com.ssafy.enjoytrip.*.model.mapper")
public class WebMVCConfig implements WebMvcConfigurer {

    private final JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
            .addPathPatterns("/**")
            .excludePathPatterns("/swagger-ui/**", "/swagger-resources/**", "/v2/api-docs")
            .excludePathPatterns("/webjars/**", "/error/**", "/api/**", "/h2-console/**",
                "/favicon.ico");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .maxAge(1800);
    }
}
