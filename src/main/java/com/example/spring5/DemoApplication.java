package com.example.spring5;

import java.util.Locale;

import com.example.logic.ann.AwareBean;
import com.example.logic.ann.Printer;
import com.example.logic.ann.SimpleBean;


import org.springframework.beans.factory.Aware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ResourceBundleMessageSource;


public class DemoApplication {
	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext("com.example.logic.ann");

		System.out.println();

//		Printer p = context.getBean("simplePrinter", Printer.class);
//		System.out.println();
//		p.print1("hello");
//		p.print2("hello");

		SimpleBean simpleBean = context.getBean("simpleBean", SimpleBean.class);
		simpleBean.sayHello();

		System.out.println(context.getMessage("name", null, Locale.ENGLISH));
		System.out.println(context.getMessage("name", null, Locale.FRENCH));





	}

	private static String getScope(ApplicationContext context, Class<?> cls){
		ConfigurableListableBeanFactory factory = ((ConfigurableApplicationContext)context).getBeanFactory();
		for(String name: factory.getBeanNamesForType(cls)){
			return factory.getBeanDefinition(name).getScope();
		}
		return null;
	}
}





