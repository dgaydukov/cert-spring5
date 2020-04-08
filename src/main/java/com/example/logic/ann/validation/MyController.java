package com.example.logic.ann.validation;

import javax.validation.Valid;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/employee")
@RequiredArgsConstructor
public class MyController {
    private final EmployeeValidator employeeValidator;

    /**
     * If we don't specify model to which apply validation, it would be applied to all methods in controller
     */
    @InitBinder("employee")
    public void addValidation(WebDataBinder binder) {
        binder.addValidators(employeeValidator);
    }


    /**
     * If we don't put errors as second param - exception would be thrown in case validation failed
     */
    @PostMapping
    public ResponseEntity save(@RequestBody @Valid Employee emp, Errors err){
        System.out.println(err);
        if(err.hasErrors()) {
            List<String> errors = err.getAllErrors().stream().map(e->e.getDefaultMessage()).collect(Collectors.toList());
            return new ResponseEntity<>(Map.of("object", emp, "errors", errors), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(emp, HttpStatus.OK);
    }
}