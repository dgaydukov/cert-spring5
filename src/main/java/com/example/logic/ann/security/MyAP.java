package com.example.logic.ann.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MyAP implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("MyAP.authenticate => " + authentication);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication auth = new UsernamePasswordAuthenticationToken("user", "password", List.of(()->"ROLE_USER"));
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);
        return auth;
    }

    @Override
    public boolean supports(Class<?> cls) {
        System.out.println("MyAP.supports => " + cls);
        return true;
    }
}
