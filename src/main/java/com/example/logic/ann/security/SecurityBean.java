package com.example.logic.ann.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SecurityBean {
    @Autowired
    private List<Filter> filters;

    @Autowired
    private FilterChainProxy filterChain;

    @Autowired
    private UserDetailsService user;

    public void printFilters() {
        System.out.println(user.loadUserByUsername("user").getPassword());
        System.out.println(user.loadUserByUsername("admin").getPassword());
        System.out.println();
        List<String> securityFilters =
                filterChain.getFilterChains().stream()
//                filters.stream()
//                .filter(f->f.getClass().equals(FilterChainProxy.class))
//                .map(f->(FilterChainProxy)f)
//                .flatMap(f->f.getFilterChains().stream())
                .flatMap(f->f.getFilters().stream())
                .map(f->f.getClass().getName())
                .collect(Collectors.toList());
        System.out.println("securityFilters => " + securityFilters.size());
        securityFilters.forEach(System.out::println);
    }
}
