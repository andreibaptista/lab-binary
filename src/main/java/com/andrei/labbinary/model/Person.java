package com.andrei.labbinary.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Person {

    @JsonCreator
    public Person(@JsonProperty("name") String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    long id;

    @Column(name = "name")
    String name;

    public Person() {

    }
}
