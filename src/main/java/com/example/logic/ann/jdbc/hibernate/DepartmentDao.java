package com.example.logic.ann.jdbc.hibernate;

import javax.transaction.Transactional;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.logic.ann.jdbc.MyDao;
import com.example.logic.ann.jdbc.hibernate.entities.DepartmentEntity;

@Transactional
@Repository
public class DepartmentDao implements MyDao<DepartmentEntity> {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<DepartmentEntity> getAll() {
        return sessionFactory.getCurrentSession().createQuery("from DepartmentEntity d").list();
    }

    @Override
    public DepartmentEntity getById(int id) {
        //return (DepartmentEntity) sessionFactory.getCurrentSession().getNamedQuery("DepartmentEntity.GET_BY_ID_EAGER").setParameter("id", id).uniqueResult();
        return (DepartmentEntity) sessionFactory.getCurrentSession().createQuery("from DepartmentEntity d where d.id=:id").setParameter("id", id).uniqueResult();
    }

    @Override
    public boolean deleteById(int id) {
        return sessionFactory.getCurrentSession().createQuery("delete from DepartmentEntity d where d.id=:id").setParameter("id", id).executeUpdate() == 1;
    }

    @Override
    public DepartmentEntity save(DepartmentEntity model) {
        sessionFactory.getCurrentSession().saveOrUpdate(model);
        return model;
    }
}
