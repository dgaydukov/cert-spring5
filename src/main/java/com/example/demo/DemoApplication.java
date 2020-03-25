package com.example.demo;

import com.example.spring.annotation.AnSimpleBean;
import com.example.spring.annotation.Printer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class DemoApplication {
	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext("com.example.spring.annotation");
		Printer p = context.getBean("anSimplePrinter", Printer.class);
		System.out.println();
		p.print1("hello");
		p.print2("hello");

		AnSimpleBean simpleBean = context.getBean("anSimpleBean", AnSimpleBean.class);
		simpleBean.print();
	}
}