package com.example.logic.ann;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.Filter;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.AliasFor;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Service;

import com.example.logic.ann.postprocessors.annotation.PostAppReady;

@Service
public class SimpleBean {
    @Value("goodBean")
    private String name;
    @Autowired
    private Printer printer;

    @Autowired
    @Qualifier("springSecurityFilterChain")
    private Filter filter;


    public SimpleBean() {
        System.out.println("constructing SimpleBean...");
    }

    @PostConstruct
    public void init1() {
        System.out.println("initializing SimpleBean, phase 1...");
    }

    public void init2() {
        System.out.println("initializing SimpleBean, phase 2...");
    }

    @PostAppReady
    public void init3() {
        System.out.println("initializing SimpleBean, phase 3...");
    }

    public void sayHello() {
        printer.print("I'm SimpleBean, my name is " + name);
    }

    public void printFilters() {
        var filters = (((FilterChainProxy) filter)).getFilterChains()
                .stream()
                .flatMap(chain -> chain.getFilters().stream())
                .collect(Collectors.toList());
        System.out.println("printFilters => " + filters.size() + " security filters");
        filters.stream().forEach(filter -> System.out.println(filter.getClass().getName()));
    }
}