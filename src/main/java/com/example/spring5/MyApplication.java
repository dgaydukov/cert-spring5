package com.example.spring5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.example.logic.ann")
public class MyApplication {
	public static void main(String[] args) {
		var context = SpringApplication.run(MyApplication.class, args);
		System.out.println(context);
	}
}
