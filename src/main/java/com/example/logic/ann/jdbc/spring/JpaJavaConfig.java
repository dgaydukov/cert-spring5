package com.example.logic.ann.jdbc.spring;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.sql.Driver;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource("jdbc.properties")
@EnableJpaRepositories("com.example.logic.ann.jdbc.jpa.repository")
public class JpaJavaConfig {
    @Value("${driverClassName}")
    private String driverClassName;
    @Value("${url}")
    private String url;
    /**
     * If you use `user/username` it would be the name of your machine (diman),
     * cause in spring env variables overwrite configs
     */
    @Value("${dbUser}")
    private String username;
    @Value("${password}")
    private String password;

    @Bean
    public DataSource dataSource() {
        return new DriverManagerDataSource(url, username, password);
    }

    private Properties properties() {
        Properties props = new Properties();
        /**
         * this config allows to lazy access after session is closed
         * but you shouldn't use it cause it's anti-pattern
         */
        //props.put("hibernate.enable_lazy_load_no_trans", true);

        props.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        props.put("hibernate.format_sql", true);
        props.put("hibernate.use_sql_comments", true);
        props.put("hibernate.show_sql", true);
        return props;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        var bean = new LocalContainerEntityManagerFactoryBean();
        bean.setPackagesToScan("com.example.logic.ann.jdbc.spring");
        bean.setDataSource(dataSource());
        bean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        bean.setJpaProperties(properties());
        bean.afterPropertiesSet();
        return bean.getNativeEntityManagerFactory();
    }

    /**
     * bean should be named `transactionManager`, otherwise you got exception
     * `No matching TransactionManager bean found for qualifier 'transactionManager' - neither qualifier match nor bean name match!`\
     */
    @Bean
    public TransactionManager transactionManager(){
        return new JpaTransactionManager(entityManagerFactory());
    }
}
