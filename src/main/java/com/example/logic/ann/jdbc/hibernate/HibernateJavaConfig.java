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
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.p6spy.engine.spy.P6DataSource;

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
    public DataSource ds() {
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

    @Bean
    public DataSource dataSource() {
        var ds = new DriverManagerDataSource();
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        return new P6DataSource(ds);
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
    public SessionFactory sessionFactory() {
        try{
            LocalSessionFactoryBean sessionBean = new LocalSessionFactoryBean();
            sessionBean.setDataSource(dataSource());
            sessionBean.setHibernateProperties(properties());
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
