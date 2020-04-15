package com.example.logic.ann.reactive;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.server.SecurityWebFilterChain;

import reactor.core.publisher.Mono;

/**
 * Example for setting up security for webflux app
 * We don't need to extend from WebSecurityConfigurerAdapter and override any configure methods
 * We have 2 beans, one to configure http security and another to configure user details
 */
@Configuration
@EnableWebFluxSecurity
public class ReactiveJavaConfig {
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
            .authorizeExchange()
            .pathMatchers("/person").hasAuthority("USER")
            .anyExchange().permitAll()
            .and()
            .build();
    }

    @Bean
    public ReactiveUserDetailsService reactiveUserDetailsService(){
        return username -> Mono.just(new User(username, "password", List.of(()->"ROLE_USER")));
    }
}