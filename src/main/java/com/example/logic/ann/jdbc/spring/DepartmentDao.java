package com.example.logic.ann.jdbc.spring;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.logic.ann.jdbc.DepartmentModel;
import com.example.logic.ann.jdbc.MyDao;

@Component
public class DepartmentDao implements MyDao<DepartmentModel> {
    @Autowired
    private EntityManagerFactory entityManagerFactory;
    @Autowired
    private EntityManager entityManager;
    @PersistenceContext
    private EntityManager entityManager2;

    @Override
    public List<DepartmentModel> getAll() {
        return null;
    }

    @Override
    public DepartmentModel getById(int id) {
        return null;
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }

    @Override
    public DepartmentModel save(DepartmentModel model) {
        System.out.println("saving...");
        System.out.println(entityManagerFactory.createEntityManager());
        System.out.println(entityManager);
        System.out.println(entityManager2);
        return null;
    }
}
