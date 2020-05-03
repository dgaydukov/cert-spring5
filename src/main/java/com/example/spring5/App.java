package com.example.spring5;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import({My.class})
@SpringBootApplication
public class App{
    public static void main(String[] args) {

    }
}


class My{
    public My(){
        System.out.println("constructor");
    }
}
