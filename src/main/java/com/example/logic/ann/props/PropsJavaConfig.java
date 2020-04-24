package com.example.logic.ann.props;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

/**
 * ConfigurationProperties - enables by default in spring boot, but if you use other appContext you should enable them explicitly\
 */
@Configuration
@PropertySource("props/person.properties")
@EnableConfigurationProperties
public class PropsJavaConfig {
    /**
     * This bean is the same as adding @PropertySource("application.properties") to configuration class
     * since it BFPP is should be static
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(){
        PropertySourcesPlaceholderConfigurer propConfig = new PropertySourcesPlaceholderConfigurer();
        propConfig.setLocation(new ClassPathResource("application.properties"));
        return propConfig;
    }
}
