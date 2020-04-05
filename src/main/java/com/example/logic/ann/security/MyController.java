package com.example.logic.ann.security;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController {

    @GetMapping("/")
    public ResponseEntity<String> handleGet(){
        System.out.println("handleGet => " + SecurityContextHolder.getContext().getAuthentication());
        return new ResponseEntity<>("it works!", HttpStatus.OK);
    }

    @GetMapping("/api/test")
    public ResponseEntity<String> handleApiTest(){
        System.out.println("handleApiTest => " + SecurityContextHolder.getContext().getAuthentication());
        return new ResponseEntity<>("it works!", HttpStatus.OK);
    }
}
