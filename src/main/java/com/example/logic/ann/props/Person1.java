package com.example.logic.ann.props;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.ToString;

@Component
@ToString
public class Person1 {
    @Value("1")
    private int id;

    @Value("${name}")
    private String name;

    @Value("${age}")
    private int age;
}
