package com.example.logic.ann.hateoas;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Data;

@Data
@Relation(value="person", collectionRelation="people")
public class Person extends RepresentationModel<Person> {
    private int id;
    private String name;
    private String email;
    private String phone;
}
