package com.example.spring5;

import com.example.logic.ann.SimpleBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@ComponentScan("com.example.logic.ann")
@EnableWebSecurity
public class MyApplication {
	public static void main(String[] args) {
		var ctx = SpringApplication.run(MyApplication.class, args);
		SimpleBean sb = ctx.getBean(SimpleBean.class);
		sb.printFilters();
	}
}