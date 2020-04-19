package com.example.logic.ann.misc;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
public class MyController {
    @GetMapping
    public void handleGet(){
        System.out.println("handleGet");
        throw new MyException("Oops....");
    }
}

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
class MyException extends RuntimeException {
    public MyException(String str){
        super(str);
    }
}
