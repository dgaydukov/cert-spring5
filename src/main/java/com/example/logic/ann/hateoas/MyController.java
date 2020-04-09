package com.example.logic.ann.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/person")
public class MyController {
    private List<Person> persons = new ArrayList<>();

    @PostConstruct
    public void init(){
        Person p = new Person();
        p.setId(1);
        p.setName("John");
        p.setEmail("john@mail.com");
        p.setPhone("+123456789");
        persons.add(p);
    }

    @GetMapping
    public ResponseEntity<List<Person>> getPersons(){
        persons.forEach(this::addLinks);
        return new ResponseEntity<>(persons, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Person> addPerson(@RequestBody Person person){
        persons.add(person);
        return new ResponseEntity<>(person, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable int id) {
        Optional<Person> person = persons.stream().filter(p->p.getId()==id).findFirst();
        person.ifPresent(this::addLinks);
        return new ResponseEntity<>(person.orElse(null), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable int id, @RequestBody Person person) {
        Optional<Person> current = persons.stream().filter(p->p.getId()==id).findFirst();
        if (current.isEmpty()) {
            return new ResponseEntity<>(person, HttpStatus.NOT_FOUND);
        }
        persons.removeIf(p->p.getId()==id);
        persons.add(person);
        return new ResponseEntity<>(person, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePerson(@PathVariable int id) {
        persons.removeIf(p->p.getId()==id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    private void addLinks(Person p){
        p.add(linkTo(MyController.class).withRel("persons"));
        p.add(linkTo(MyController.class).withRel("addPerson"));
        p.add(linkTo(MyController.class).slash(p.getId()).withRel("getPerson"));
        p.add(linkTo(MyController.class).slash(p.getId()).withRel("updatePerson"));
        p.add(linkTo(MyController.class).slash(p.getId()).withRel("deletePerson"));
    }
}