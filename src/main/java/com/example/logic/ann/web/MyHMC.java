package com.example.logic.ann.web;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;

public class MyHMC extends AbstractHttpMessageConverter<Person> {
    public MyHMC(){
        super(new MediaType("text", "person"));
    }

    @Override
    protected boolean supports(Class<?> cls) {
        return cls == Person.class;
    }

    @Override
    protected Person readInternal(Class<? extends Person> cls, HttpInputMessage input) throws IOException, HttpMessageNotReadableException {
        try(Scanner scanner = new Scanner(input.getBody())) {
            Person p = new Person();
            String[] arr = scanner.next().split("/");
            p.setAge(Integer.parseInt(arr[0]));
            p.setName(arr[1]);
            return p;
        }
    }

    @Override
    protected void writeInternal(Person person, HttpOutputMessage output) throws IOException, HttpMessageNotWritableException {
        try(OutputStream stream = output.getBody()){
            stream.write(person.toString().getBytes());
        }
    }
}
