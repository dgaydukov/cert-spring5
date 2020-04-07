package com.example.logic.ann.batch;

import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@EnableBatchProcessing
public class BatchJavaConfig {
    private static final String JOP_NAME = "myJob";
    private static final String STEP_NAME = "myStep";

    @Autowired
    private JobBuilderFactory jobFactory;

    @Autowired
    private StepBuilderFactory stepFactory;

    @Autowired
    private StepExecutionListener listener;

    @Bean
    public Job job(Step step){
        return jobFactory.get(JOP_NAME).start(step).build();
    }

    @Bean
    public Step step(ItemReader<String> reader, ItemProcessor<String, Integer> processor, ItemWriter<Integer> writer){
        return stepFactory.get(STEP_NAME)
            .listener(listener)
            .<String, Integer>chunk(5)
            .reader(reader)
            .processor(processor)
            .writer(writer)
            .build();
    }

    @Bean
    public ItemReader<String> itemReader(){
        return new ListItemReader<>(List.of("a", "bb", "ccc"));
    }

    @Bean
    public ItemWriter<Integer> itemWriter(){
        return list -> System.out.println("write => " + list);
    }
}
