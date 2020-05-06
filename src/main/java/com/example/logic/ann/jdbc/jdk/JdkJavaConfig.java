package com.example.logic.ann.jdbc.jdk;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("jdbc.properties")
public class JdkJavaConfig {
    @Value("${url}")
    private String url;
    @Value("${dbUser}")
    private String username;
    @Value("${password}")
    private String password;

    @Bean
    public Connection connection(){
        try{
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException ex){
            throw new RuntimeException(ex);
        }
    }

    @Bean
    public DepartmentDao departmentDao(){
        return new DepartmentDao();
    }
}
