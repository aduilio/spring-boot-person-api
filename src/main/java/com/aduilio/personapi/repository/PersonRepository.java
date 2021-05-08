package com.aduilio.personapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aduilio.personapi.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
