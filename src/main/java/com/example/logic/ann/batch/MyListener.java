package com.example.logic.ann.batch;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class MyListener implements StepExecutionListener {

    @Override
    public void beforeStep(StepExecution step) {
        System.out.println("before => " + step);
    }

    @Override
    public ExitStatus afterStep(StepExecution step) {
        System.out.println("after => " + step);
        return null;
    }
}
