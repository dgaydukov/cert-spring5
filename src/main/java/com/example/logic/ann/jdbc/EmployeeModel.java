package com.example.logic.ann.jdbc;

import lombok.Data;

@Data
public class EmployeeModel {
    private int id;
    private String name;
    private int salary;
    private int departmentId;
}
