package com.example.logic.ann.web.filters;

import javax.servlet.*;
import java.io.IOException;

//@Component
public class MyNewFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("MyNewFilter.doFilter");
    }
}
