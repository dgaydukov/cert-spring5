package com.example.logic.ann.jdbc.spring;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
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

    @PersistenceUnit
    EntityManagerFactory factory;
    @PersistenceContext
    EntityManager manager;

    @Override
    public List<DepartmentModel> getAll() {
        return null;
    }

    @Override
    public DepartmentModel getById(int id) {
        return null;
    }

    @Override
    public DepartmentModel save(DepartmentModel model) {
        System.out.println("saving...");
        return null;
    }

    @Override
    public void delete(DepartmentModel model) {

    }
}
