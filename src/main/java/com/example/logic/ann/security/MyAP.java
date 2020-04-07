package com.example.logic.ann.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class MyAP implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        System.out.println("MyAP.authenticate => " + auth);
        return auth;
    }

    @Override
    public boolean supports(Class<?> cls) {
        System.out.println("MyAP.supports => " + cls);
        return true;
    }
}
