package com.ssafy.enjoytrip.global.config;

import com.ssafy.enjoytrip.global.auth.interceptor.JwtArgumentResolver;
import com.ssafy.enjoytrip.global.auth.interceptor.JwtInterceptor;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
@MapperScan(basePackages = "com.ssafy.enjoytrip.*.*.model.mapper")
public class WebMVCConfig implements WebMvcConfigurer {

    private final JwtInterceptor jwtInterceptor;
    private final JwtArgumentResolver jwtArgumentResolver;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
            .addPathPatterns("/**")
            .excludePathPatterns("/swagger-ui/**", "/swagger-resources/**", "/v2/api-docs")
            .excludePathPatterns("/webjars/**", "/error/**", "/api/**", "/h2-console/**",
                "/favicon.ico");
    }

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .maxAge(1800);
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(jwtArgumentResolver);
    }
}
