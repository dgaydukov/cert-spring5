package com.example.spring5;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;


public class App{
    public static void main(String[] args) {
        /**
         * We need to set security context first and set it to shared-thread mode
         */
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        Authentication auth = new UsernamePasswordAuthenticationToken("user", "password", List.of(()->"ROLE_USER"));
        securityContext.setAuthentication(auth);
        SecurityContextHolder.setContext(securityContext);
        var context = new AnnotationConfigApplicationContext("com.example.logic.ann.integration");
    }
}