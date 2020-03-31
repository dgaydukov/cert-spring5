package com.example.logic.ann.jdbc.spring;

import javax.sql.DataSource;

import java.sql.Driver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

@Configuration
@PropertySource("jdbc.properties")
public class JdbcJavaConfig {
    @Value("${driverClassName}")
    private String driverClassName;

    @Value("${url}")
    private String url;

    /**
     * If you use `user/username` it would be the name of your machine (diman),
     * cause in spring env variables overwrite configs
     */
    @Value("${dbUser}")
    private String username;

    @Value("${password}")
    private String password;

    /**
     * This bean should be static, cause it needs to run before class loaded, so it can insert values under `@Value`.
     * If you remove static, all values (url, username, password) would be null
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(){
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    @Lazy
    public DataSource dataSource() throws ClassNotFoundException {
        SimpleDriverDataSource ds = new SimpleDriverDataSource();
        ds.setDriverClass((Class<? extends Driver>) Class.forName(driverClassName));
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        return ds;
    }
}
