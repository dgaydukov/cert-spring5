package com.example.logic.ann.misc;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class MyController {
    @GetMapping("/a")
    public void handleGet(){
        System.out.println("handleGet");
        throw new MyException1();
    }
    @GetMapping("/b")
    public void handleGet2(){
        System.out.println("handleGet2");
        throw new MyException2();
    }
}



@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "oops 400")
class MyException1 extends RuntimeException{}

class MyException2 extends RuntimeException{}


@ControllerAdvice
class MyExceptionHandler{
    @ExceptionHandler(MyException2.class)
    public ResponseEntity<String> handleMyException2(){
        System.out.println("handleException");
        return new ResponseEntity<>("oops 400", HttpStatus.NOT_FOUND);
    }
}