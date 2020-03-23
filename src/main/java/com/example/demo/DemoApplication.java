package com.example.demo;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import com.example.demo.xml.MyService;
import com.example.demo.xml.MyStaticFactoryService;

import javax.swing.*;

@SpringBootApplication
public class DemoApplication {
	public static void main(String[] args) {
		 ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);
		 System.out.println(context.getClass());

		//ApplicationContext context = new ClassPathXmlApplicationContext("xmlconfig.xml");

		//ApplicationContext context = new FileSystemXmlApplicationContext("src/main/resources/xmlconfig.xml");
//		System.out.println();
//		GenericApplicationContext context = new GenericApplicationContext();
//		int beansCount = new XmlBeanDefinitionReader(context).loadBeanDefinitions("xmlconfig.xml");
//		System.out.println("found "+beansCount+" beans for xmlconfig.xml");
//		context.refresh();
//
//
//
//		System.out.println(context);
//		MyService service = context.getBean("myXmlService", MyService.class);
//		service.print();
//		MyStaticFactoryService factoryService = context.getBean("myStaticFactoryService", MyStaticFactoryService.class);
//		factoryService.print();

		System.out.println();
// search all beans annotated with @Component or any other annotation that includes component
//		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
//		ctx.scan("com.example.demo.annotation");
//		ctx.refresh();

//		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext("com.example.demo.annotation");
//
//		System.out.println(ctx);
//
//		MyService myService = context.getBean(MyService.class);
//		myService.print();



	}
}