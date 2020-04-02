package com.example.logic.ann.jdbc.hibernate;

import java.util.List;

public interface MyDao<T> {
    List<T> getAll();
    T getById(int id);
    T save(T model);
    void delete(T model);
}
