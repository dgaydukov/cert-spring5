package com.example.logic.ann.jdbc.spring.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.logic.ann.jdbc.hibernate.entities.DepartmentEntity;

public interface DepartmentRepository extends CrudRepository<DepartmentEntity, Integer> {
}
