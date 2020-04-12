package com.example.logic.ann.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebJavaConfig implements WebMvcConfigurer {
    /**
     * You can register new HttpMessageConverter by either addging a Bean
     * or overriding configureMessageConverters
     */
    @Bean
    public HttpMessageConverter<Person> httpMessageConverter(){
        return new MyHMC();
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MyHMC());
    }
}
