package com.example.logic.ann.jdbc.spring;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class DepartmentModel {
    private int id;
    private String name;
    private String type;
}
