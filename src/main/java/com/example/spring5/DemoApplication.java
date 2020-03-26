package com.example.spring5;

import com.example.logic.ann.SimpleBean;
import com.example.logic.ann.Printer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class DemoApplication {
	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext("com.example.logic.ann");
		Printer p = context.getBean("simplePrinter", Printer.class);
		System.out.println();
		p.print1("hello");
		p.print2("hello");

		SimpleBean simpleBean = context.getBean("simpleBean", SimpleBean.class);
		simpleBean.print();
	}
}