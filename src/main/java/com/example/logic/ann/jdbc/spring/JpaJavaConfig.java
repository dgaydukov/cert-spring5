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
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
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

    private Properties properties() {
        Properties props = new Properties();
        /**
         * this config allows to lazy access after session is closed
         * but you shouldn't use it cause it's antipattern
         */
        //props.put("hibernate.enable_lazy_load_no_trans", true);

        props.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        props.put("hibernate.format_sql", true);
        props.put("hibernate.use_sql_comments", true);
        props.put("hibernate.show_sql", true);
        return props;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter(){
        return new HibernateJpaVendorAdapter();
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        var bean = new LocalContainerEntityManagerFactoryBean();
        bean.setPackagesToScan("com.example.logic.ann.jdbc.jpa");
        bean.setDataSource(dataSource());
        bean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        bean.setJpaProperties(properties());
        bean.setJpaVendorAdapter(jpaVendorAdapter());
        bean.afterPropertiesSet();
        return bean.getNativeEntityManagerFactory();
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager(){
        return new JpaTransactionManager(entityManagerFactory());
    }
}
