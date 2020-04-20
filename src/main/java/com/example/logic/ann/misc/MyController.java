package com.example.logic.ann.misc;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@Component
public class MyController {
    @GetMapping
    public void handleGet(){
        System.out.println("handleGet");
    }
}


