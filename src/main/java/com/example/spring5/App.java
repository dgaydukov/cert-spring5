package com.example.spring5;

import javax.sql.DataSource;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class App {
    public static void main(String[] args) throws SQLException {
        ApplicationContext context = new AnnotationConfigApplicationContext("com.example.logic.ann.jdbc.spring");
        System.out.println(context.getBean(DataSource.class).getConnection());
    }
}