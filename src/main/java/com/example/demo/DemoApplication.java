package com.example.demo;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import com.example.demo.xmlconfig.MyService;
import com.example.demo.xmlconfig.MyStaticFactoryService;

import java.lang.management.ManagementFactory;
import java.util.Random;

@SpringBootApplication
public class DemoApplication {
	public static void main(String[] args) {
		//ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);

		//ApplicationContext context = new ClassPathXmlApplicationContext("xmlconfig.xml");

		//ApplicationContext context = new FileSystemXmlApplicationContext("src/main/resources/xmlconfig.xml");

		GenericApplicationContext context = new GenericApplicationContext();
		new XmlBeanDefinitionReader(context).loadBeanDefinitions("xmlconfig.xml");
		context.refresh();



		System.out.println(context);
		MyService service = context.getBean("myXmlService", MyService.class);
		service.print();
		MyStaticFactoryService factoryService = context.getBean("myStaticFactoryService", MyStaticFactoryService.class);
		factoryService.print();

	}
}
