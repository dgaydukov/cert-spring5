package com.example.logic.ann.security;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

//@Component
public class MySecurityFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        System.out.println("MySecurityFilter.doFilter");
        chain.doFilter(req, res);
    }
}
