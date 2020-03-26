package com.example.logic.annotation.postprocessors;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.example.logic.annotation.Printer;

@Component
public class MyListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Printer printer = event.getApplicationContext().getBean(Printer.class);
        System.out.println(printer.getClass());
        System.out.println("ContextRefreshed => " + event);
    }
}
