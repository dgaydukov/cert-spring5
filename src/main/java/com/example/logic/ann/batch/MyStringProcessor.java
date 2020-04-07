package com.example.logic.ann.batch;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class MyStringProcessor implements ItemProcessor<String, Integer> {
    @Override
    public Integer process(String str) throws Exception {
        System.out.println("processing => " + str);
        Thread.sleep(1000);
        return str.length();
    }
}
