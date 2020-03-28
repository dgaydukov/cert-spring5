package com.example.logic.ann.web.filters;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.Filter;

//@Configuration
public class FilterJavaConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public FilterRegistrationBean<Filter> myFilter() {
        var filter = new FilterRegistrationBean<>();
        filter.setFilter(new MyFilter());
        filter.addUrlPatterns("/my");
        return filter;
    }

    /**
     * in this way we can add custom filter to chain of security filters
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterAfter(new MyFilter(), FilterSecurityInterceptor.class);
        http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
    }
}
