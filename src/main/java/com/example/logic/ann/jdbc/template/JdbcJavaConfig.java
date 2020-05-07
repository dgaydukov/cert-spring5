package com.example.logic.ann.jdbc.template;

import javax.sql.DataSource;

import java.sql.Driver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource("jdbc.properties")
@EnableTransactionManagement
public class JdbcJavaConfig {
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
    @Primary
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

    @Bean
    public DataSource ds1() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(driverClassName);
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        return ds;
        /**
         * we can also write just:
         * return new DriverManagerDataSource(url, username, password);
         */
    }

    @Bean
    public DataSource ds2() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder.setType(EmbeddedDatabaseType.H2).build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(){
        JdbcTemplate template = new JdbcTemplate();
        MySqlExceptionTranslator translator = new MySqlExceptionTranslator();
        translator.setDataSource(dataSource());
        template.setDataSource(dataSource());
        template.setExceptionTranslator(translator);
        return template;
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(){
        return new NamedParameterJdbcTemplate(jdbcTemplate());
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager(){
        return new DataSourceTransactionManager(dataSource());
    }
}
