package com.example.logic.ann.security;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController {

    @GetMapping("/")
    public ResponseEntity<String> handleGet() {
        return new ResponseEntity<>("it works!", HttpStatus.OK);
    }

    @GetMapping("/api/profile")
    public ResponseEntity<String> handleApi(){
        System.out.println("auth => " + SecurityContextHolder.getContext().getAuthentication());
        return new ResponseEntity<>("it works!", HttpStatus.OK);
    }
}
