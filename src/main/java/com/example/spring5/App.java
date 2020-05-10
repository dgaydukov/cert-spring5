package com.example.spring5;


import lombok.Data;
import lombok.SneakyThrows;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class App{
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(App.class.getPackageName());
        context.getBean(MyRepository.class).save();
    }
}

@Component
@Transactional
class MyRepository{
    @Autowired
    private SessionFactory sessionFactory;

    public void save(){
        Session session = sessionFactory.getCurrentSession();
        User user = new User();
        user.setName("Jack");
        user.setAge(30);

        Account usdAccount = new Account();
        usdAccount.setCurrency("USD");
        Account euroAccount = new Account();
        euroAccount.setCurrency("EUR");

        user.setAccounts(List.of(usdAccount, euroAccount));
        session.save(user);

        System.out.println("createQuery => " + session.createQuery("FROM Account u where id=1").uniqueResult());

        //System.out.println("createQuery => " + session.createQuery("FROM User u").list());
        //System.out.println("find => " + session.find(User.class, user.getId()));
    }
}

@Configuration
@EnableTransactionManagement
class JavaConfig{
    @Bean
    public DataSource dataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder.setType(EmbeddedDatabaseType.H2).build();
    }

    @Bean
    @SneakyThrows
    public SessionFactory sessionFactory() {
        LocalSessionFactoryBean sessionBean = new LocalSessionFactoryBean();
        sessionBean.setDataSource(dataSource());
        sessionBean.setPackagesToScan(App.class.getPackageName());
        var props = new Properties();
        // create tables in db from entities
        props.put("hibernate.hbm2ddl.auto", "create");
        sessionBean.setHibernateProperties(props);
        // initialize factory, if not called, `getObject` would return null
        sessionBean.afterPropertiesSet();
        return sessionBean.getObject();
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager(){
        return new HibernateTransactionManager(sessionFactory());
    }
}

@Entity
@Data
class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String name;
    private int age;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private List<Account> accounts = new ArrayList<>();

}

@Entity
@Data
class Account{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String currency;
    @ManyToOne
    private User user;
}