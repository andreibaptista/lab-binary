package com.andrei.labbinary.repository;

import com.andrei.labbinary.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
