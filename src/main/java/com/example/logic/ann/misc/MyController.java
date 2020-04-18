package com.example.logic.ann.misc;

import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/person")
public class MyController {
    @PostMapping
    @ResponseBody
    public Person postPerson(@RequestBody Person p, HttpServletRequest req, HttpServletResponse res, HttpSession session, WebRequest webReq){
        System.out.println(req);
        System.out.println(res);
        System.out.println(session);
        System.out.println(webReq);
        System.out.println(p);
        return p;
    }
}

@Data
class Person{
    private int age;
    private String name;
}

