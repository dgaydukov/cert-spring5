package com.example.demo;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import com.example.demo.xml.MyService;
import com.example.demo.xml.MyStaticFactoryService;

@SpringBootApplication
public class DemoApplication {
	public static void main(String[] args) {
		//ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);

		//ApplicationContext context = new ClassPathXmlApplicationContext("xmlconfig.xml");

		//ApplicationContext context = new FileSystemXmlApplicationContext("src/main/resources/xmlconfig.xml");
		System.out.println();
		GenericApplicationContext context = new GenericApplicationContext();
		new XmlBeanDefinitionReader(context).loadBeanDefinitions("xmlconfig.xml");
		context.refresh();



		System.out.println(context);
		MyService service = context.getBean("myXmlService", MyService.class);
		service.print();
		MyStaticFactoryService factoryService = context.getBean("myStaticFactoryService", MyStaticFactoryService.class);
		factoryService.print();

		System.out.println();

		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		System.out.println(ctx);
		ctx.scan("com.example.demo.annotation");
		ctx.refresh();

		MyService myService = context.getBean(MyService.class);
		myService.print();

	}
}
