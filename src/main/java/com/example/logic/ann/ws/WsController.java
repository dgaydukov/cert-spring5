package com.example.logic.ann.ws;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WsController {
    @GetMapping("/")
    public ResponseEntity<String> handleGet(){
        return new ResponseEntity<>("it works!", HttpStatus.OK);
    }
}
