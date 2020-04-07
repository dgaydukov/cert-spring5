package com.example.spring5;


import com.example.logic.ann.security.SecurityBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.context.WebApplicationContext;

@SpringBootApplication
@ComponentScan("com.example.logic.ann.security")
public class App {
    public static void main(String[] args) {
        var context = SpringApplication.run(App.class, args);
        var webContext = (WebApplicationContext) context;
        System.out.println("webContext => " + webContext.getClass().getName());
        System.out.println("servletContext => " + webContext.getServletContext().getClass().getName());
        context.getBean(SecurityBean.class).printFilters();
    }
}