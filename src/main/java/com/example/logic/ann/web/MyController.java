package com.example.logic.ann.web;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
public class MyController {
    @GetMapping
    public Person handleGet(){
        Person p = new Person();
        p.setAge(30);
        p.setName("Jack");
        return p;
    }

    @PostMapping
    public Person handlePost( Person p){
        System.out.println("handlePost => " + p);
        return p;
    }
}


