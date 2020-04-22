package com.example.logic.ann.misc;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/")
public class MyController {
    @GetMapping
    public void handleGet() {
        System.out.println("handleGet");
    }
}