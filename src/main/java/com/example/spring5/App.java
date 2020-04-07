package com.example.spring5;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Since we have one pom.xml for all examples we exclude security so our socket
 * is open
 */
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@ComponentScan("com.example.logic.ann.ws")
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}