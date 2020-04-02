package com.example.spring5;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.logic.ann.jdbc.hibernate.MyDao;
import com.example.logic.ann.jdbc.hibernate.entities.DepartmentEntity;
import com.example.logic.ann.jdbc.hibernate.entities.EmployeeEntity;

public class App{
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("com.example.logic.ann.jdbc.hibernate");
        /**
         * If you try to run `context.getBean(DepartmentDao.class);`, you will get
         * NoSuchBeanDefinitionException: No qualifying bean of type 'com.example.logic.ann.jdbc.hibernate.DepartmentDao' available
         * the reason, is since we are using @Transactional, our object is changed with proxy, that's why we should use interfaces
         */
        MyDao<DepartmentEntity> dao = context.getBean(MyDao.class);
        System.out.println(dao.getAll());

        var dep = new DepartmentEntity();
        dep.setName("cool");
        dep.setType("my");

        var emp = new EmployeeEntity();
        emp.setName("Jack");
        emp.setSalary(200);
        emp.setDepartment(dep);

        dep.setEmployees(List.of(emp));

        dep = dao.save(dep);
        System.out.println("saved => " + dep);
        System.out.println("getById("+dep.getId()+") => " + dao.getById(dep.getId()));
        dao.delete(dep);
    }
}












//        System.out.println("__RUN__");
//            var e1 = (DepartmentEntity)dao.getById(1);
//            System.out.println(e1.getName());
//            Thread.sleep(1000);
//            System.out.println(e1.getType());
//            Thread.sleep(1000);
//            System.out.println(e1.getEmployees());


//@NamedQuery(name="DepartmentEntity.GET_BY_ID", query="SELECT d FROM DepartmentEntity d JOIN FETCH  d.employees WHERE d.id=:id")

//return (DepartmentEntity) sessionFactory.getCurrentSession().getNamedQuery("DepartmentEntity.GET_BY_ID_EAGER").setParameter("id", id).uniqueResult();