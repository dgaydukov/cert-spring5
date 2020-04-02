package com.example.logic.ann.jdbc.hibernate.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import java.util.List;

import lombok.Data;

@Data
@Entity
@Table(name = "department")
@NamedQuery(name = "DepartmentEntity.GET_BY_ID", query = "SELECT d FROM DepartmentEntity d JOIN FETCH d.employees WHERE d.id=:id")
public class DepartmentEntity {
    public final static String GET_BY_ID = "DepartmentEntity.GET_BY_ID";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Version
    private int version;

    //@OneToMany(mappedBy = "department", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @OneToMany(mappedBy = "department")
    private List<EmployeeEntity> employees;
}
