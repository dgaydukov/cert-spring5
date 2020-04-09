package com.example.logic.ann.hateoas;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RestApiCall {
    public void getForObject(){
        RestTemplate rt = new RestTemplate();
        int id = 1;
        Person p1 = rt.getForObject("http://localhost:8080/person/{id}", Person.class, id);
        Person p2 = rt.getForObject("http://localhost:8080/person/{id}", Person.class, Map.of("id", id));
        Person p3 = rt.getForObject("http://localhost:8080/person/1", Person.class);
    }

    public void getForEntity(){
        RestTemplate rt = new RestTemplate();
        int id = 1;
        ResponseEntity<Person> p1 = rt.getForEntity("http://localhost:8080/person/{id}", Person.class, id);
        ResponseEntity<Person> p2 = rt.getForEntity("http://localhost:8080/person/{id}", Person.class, Map.of("id", id));
        ResponseEntity<Person> p3 = rt.getForEntity("http://localhost:8080/person/1", Person.class);
    }
}
