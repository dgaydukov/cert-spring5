package com.example.logic.ann.props;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Setter;
import lombok.ToString;

/**
 * You need to add setters methods, cause they are binded with setters
 */
@Component
@ToString
@Setter
@ConfigurationProperties
public class Person2 {
    @Value("2")
    private int id;
    private String name;
    private int age;
}
