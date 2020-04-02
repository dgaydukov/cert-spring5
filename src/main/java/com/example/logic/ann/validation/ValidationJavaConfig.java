package com.example.logic.ann.validation;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
@PropertySource("application.properties")
public class ValidationJavaConfig {

    @Bean
    public Employee employee(@Value("${localDate}") LocalDate localDate){
        Employee emp = new Employee();
        emp.setBirthDate(localDate);
        emp.setName("a");
        return emp;
    }

    /**
     * Name should be exactly conversionService
     */
    @Bean
    public ConversionServiceFactoryBean conversionService() {
        ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();
        bean.setConverters(Set.of(new StringToLocalDateConverter()));
        return bean;
    }

    /**
     * This will inject Validator validator, whenever you want
     * and you can call validator.validate anywhere
     */
    @Bean
    public LocalValidatorFactoryBean validator(){
        return new LocalValidatorFactoryBean();
    }
}
