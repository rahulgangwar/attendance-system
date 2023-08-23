package com.example.entity;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@ToString
@Getter
@Entity
public class Employee {

    @Id @GeneratedValue private Long id;
    private String name;

    public Employee() {}

    public Employee(String name) {
        this.name = name;
    }
}
