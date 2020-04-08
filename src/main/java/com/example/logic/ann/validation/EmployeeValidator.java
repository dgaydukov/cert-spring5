package com.example.logic.ann.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class EmployeeValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return cls == Employee.class;
    }

    @Override
    public void validate(Object obj, Errors err) {
        Employee emp = (Employee) obj;
        if ("Jack".equals(emp.getName())) {
            err.reject("name", "`Jack` is a bad choice");
        }
    }
}
