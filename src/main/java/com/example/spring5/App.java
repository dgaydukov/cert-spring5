package com.example.spring5;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.object.MappingSqlQuery;

import com.example.logic.ann.jdbc.spring.DepartmentDao;
import com.example.logic.ann.jdbc.spring.DepartmentModel;


public class App {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("com.example.logic.ann.jdbc.spring");
        var dao = context.getBean(DepartmentDao.class);
        System.out.println("getAll => " + dao.getAll());
        var model = new DepartmentModel();
        model.setName("finance");
        model.setType("my");
        model = dao.save(model);
        System.out.println("save => " + model);
        int id = model.getId();
        System.out.println("getById("+id+") =>" + dao.getById(id));
        System.out.println("deleteById("+id+") => " + dao.deleteById(id));
    }
}