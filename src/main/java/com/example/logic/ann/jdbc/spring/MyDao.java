package com.example.logic.ann.jdbc.spring;

import java.util.List;

public interface MyDao<T> {
    List<T> getAll();
    T getById(int id);
    boolean deleteById(int id);
    T save(T model);
}
