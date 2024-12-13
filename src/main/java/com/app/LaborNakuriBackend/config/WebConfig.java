package com.app.LaborNakuriBackend.config;

import com.app.LaborNakuriBackend.interceptor.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestInterceptor())
                .addPathPatterns("/api/**"); // Apply to specific URL patterns
    }
}
