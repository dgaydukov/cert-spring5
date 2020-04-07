package com.example.spring5;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.context.WebApplicationContext;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@ComponentScan("com.example.logic.ann.ws")
public class App {
    public static void main(String[] args) {
        var context = SpringApplication.run(App.class, args);
        var webContext = (WebApplicationContext) context;
        System.out.println("webContext => " + webContext.getClass().getName());
        System.out.println("servletContext => " + webContext.getServletContext().getClass().getName());
    }
}