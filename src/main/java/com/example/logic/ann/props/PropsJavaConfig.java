package com.example.logic.ann.props;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * ConfigurationProperties - enables by default in spring boot, but if you use other appContext you should enable them explicitly\
 */
@Configuration
@PropertySource("props/person.properties")
@EnableConfigurationProperties
public class PropsJavaConfig {
}
