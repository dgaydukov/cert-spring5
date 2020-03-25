package com.example.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import com.example.spring.annotation.AnSimpleBean;


public class DemoApplication {
	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext("com.example.spring.annotation");
		AnSimpleBean simpleBean = context.getBean("anSimpleBean", AnSimpleBean.class);
		simpleBean.print();
	}
}