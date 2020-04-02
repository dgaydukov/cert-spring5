package com.example.spring5;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.logic.ann.jdbc.hibernate.MyDao;
import com.example.logic.ann.jdbc.hibernate.entities.DepartmentEntity;

public class App {
    public static void main(String[] args) throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext("com.example.logic.ann.jdbc.hibernate");
        /**
         * If you try to run `context.getBean(DepartmentDao.class);`, you will get
         * NoSuchBeanDefinitionException: No qualifying bean of type 'com.example.logic.ann.jdbc.hibernate.DepartmentDao' available
         * the reason, is since we are using @Transactional, our object is changed with proxy, that's why we should use interfaces
         */
        MyDao<DepartmentEntity> dao = context.getBean(MyDao.class);
        System.out.println("__RUN__");
        var e1 = (DepartmentEntity) dao.getById(1);
        System.out.println(e1.getName());
        Thread.sleep(1000);
        System.out.println(e1.getType());
        Thread.sleep(1000);
        System.out.println(e1.getEmployees());
    }
}