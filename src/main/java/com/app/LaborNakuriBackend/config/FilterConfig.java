package com.app.LaborNakuriBackend.config;

import com.app.LaborNakuriBackend.filter.SwaggerAuthFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<SwaggerAuthFilter> swaggerFilter() {
        FilterRegistrationBean<SwaggerAuthFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new SwaggerAuthFilter());
        registrationBean.addUrlPatterns("/swagger-ui/*", "/v3/api-docs/*");
        return registrationBean;
    }
}
