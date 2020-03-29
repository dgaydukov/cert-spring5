package com.example.logic.ann.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityJavaConfig extends WebSecurityConfigurerAdapter {

    /**
     * in this way we can add custom filter to chain of security filters
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http.addFilterAfter(new MyFilter(), FilterSecurityInterceptor.class);
        //http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
    }

    @Override
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        System.out.println("__HE");
        builder.inMemoryAuthentication().withUser("admin").password("admin").roles("admin");
    }
}
