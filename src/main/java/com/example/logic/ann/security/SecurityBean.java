package com.example.logic.ann.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SecurityBean {
    @Autowired
    private List<Filter> filters;


    public void printFilters() {
        System.out.println("filters => " + filters.size());
        filters.stream().map(f->f.getClass().getName()).forEach(System.out::println);
        System.out.println();

        List<String> securityFilters = filters.stream()
                .filter(f->f instanceof FilterChainProxy)
                .flatMap(f->((FilterChainProxy)f).getFilterChains().stream())
                .flatMap(f->f.getFilters().stream())
                .map(f->f.getClass().getName())
                .collect(Collectors.toList());
        System.out.println("securityFilters => " + securityFilters.size());
        securityFilters.forEach(System.out::println);
    }
}
