package com.aduilio.personapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aduilio.personapi.entity.Person;
import com.aduilio.personapi.repository.PersonRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PersonService {

	@Autowired
	private final PersonRepository personRepository;

	public Long create(final Person person) {
		return personRepository.save(person)
				.getId();
	}

	public Optional<Person> read(final Long id) {
		return personRepository.findById(id);
	}

	public List<Person> list() {
		return personRepository.findAll();
	}

	public void delete(final Long id) {
		personRepository.deleteById(id);
	}

	public void update(final Person person) {
		personRepository.save(person);
	}
}
