package com.example.spring5;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.logic.ann.SimpleBean;

public class DemoApplication {
	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext("com.example.logic.ann");

		SimpleBean simpleBean = context.getBean("simpleBean", SimpleBean.class);
		simpleBean.sayHello();
	}
}
