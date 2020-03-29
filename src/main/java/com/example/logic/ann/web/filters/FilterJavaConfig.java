package com.example.logic.ann.web.filters;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class FilterJavaConfig {
    @Bean
    public FilterRegistrationBean<Filter> myFilter() {
        var filter = new FilterRegistrationBean<>();
        filter.setFilter(new MyFilter());
        filter.addUrlPatterns("/my");
        return filter;
    }
}
