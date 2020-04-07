package com.example.spring5;

import java.time.LocalDateTime;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan("com.example.logic.ann.batch")
public class App {
    public static void main(String[] args) throws Exception {
        var context = SpringApplication.run(App.class, args);
        System.out.println("context => " + context.getClass().getName());

        Job job = context.getBean(Job.class);
        JobLauncher jobLauncher = context.getBean(JobLauncher.class);
        JobParameters jobParameters = new JobParametersBuilder()
            .addString("date", LocalDateTime.now().toString())
            .toJobParameters();
        jobLauncher.run(job,  jobParameters);
    }
}