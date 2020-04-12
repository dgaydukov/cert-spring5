package com.example.logic.ann.web.filters;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component
public class MyNewFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        System.out.println("MyNewFilter.doFilter");
        chain.doFilter(req, res);
    }
}
