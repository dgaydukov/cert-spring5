package com.example.logic.ann.jdbc.hibernate;

import javax.sql.DataSource;

import java.io.IOException;
import java.sql.Driver;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource("jdbc.properties")
public class HibernateJavaConfig {
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
    public DataSource simpleDs() {
        try{
            SimpleDriverDataSource ds = new SimpleDriverDataSource();
            ds.setDriverClass((Class<? extends Driver>) Class.forName(driverClassName));
            ds.setUrl(url);
            ds.setUsername(username);
            ds.setPassword(password);
            return ds;
        } catch (ClassNotFoundException ex){
            throw new RuntimeException(ex);
        }
    }

    private Properties propserties() {
        Properties props = new Properties();
        //uncomment this line for lazy to work
        //props.put("hibernate.enable_lazy_load_no_trans", true);
//        props.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
//        props.put("hibernate.format_sql", true);
//        props.put("hibernate.use_sql_comments", true);
//        props.put("hibernate.show_sql", true);
//        props.put("hibernate.max_fetch_depth", 3);
//        props.put("hibernate.jdbc.batch_size", 10);
//        props.put("hibernate.jdbc.fetch_size", 50);
        return props;
    }

    @Bean
    public SessionFactory sessionFactory() {
        try{
            LocalSessionFactoryBean sessionBean = new LocalSessionFactoryBean();
            sessionBean.setDataSource(simpleDs());
            sessionBean.setHibernateProperties(propserties());
            sessionBean.setPackagesToScan("com.example.logic.ann.jdbc.hibernate.entities");
            sessionBean.afterPropertiesSet();
            return sessionBean.getObject();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager(){
        return new HibernateTransactionManager(sessionFactory());
    }
}
