package com.example.logic.ann.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityJavaConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private AuthenticationProvider provider;

    /**
     * in this way we can add custom filter to chain of security filters
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http.addFilterAfter(new MySecurityFilter(), ExceptionTranslationFilter.class);
        //http.antMatcher("/api/*").authorizeRequests().anyRequest().authenticated();
        /**
         * just add filter for all `api` requests, and then decide what to do inside filter
         */
        http.antMatcher("/api/*").addFilterBefore(new MySecurityFilter(), BasicAuthenticationFilter.class);

    }

    /**
     * ignoring - omit spring security at all, it's overwrite path from HttpSecurity
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/");
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(provider);
    }
}