package com.example.spring5;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

public class App{
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext("com.example.logic.ann.jdbc.template");
        var template = context.getBean(JdbcTemplate.class);
        try {
            template.queryForObject("select count(*) from no_table", Object.class);
        } catch (DataAccessException ex){
            System.out.println(ex.getClass() + " => " + ex.getLocalizedMessage());
            System.out.println(ex.getCause().getClass() + " => " + ex.getCause().getLocalizedMessage());
        }
    }
}