//package com.app.LaborNakuriBackend.config;
//
//import com.app.LaborNakuriBackend.filter.RequestResponseFilter;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class FilterConfig {
//
//    @Bean
//    public FilterRegistrationBean<RequestResponseFilter> loggingFilter() {
//        FilterRegistrationBean<RequestResponseFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(new RequestResponseFilter());
//        registrationBean.addUrlPatterns("/api/*"); // Apply to specific URL patterns
//        return registrationBean;
//    }
//}
