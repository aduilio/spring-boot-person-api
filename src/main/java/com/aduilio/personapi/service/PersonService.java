package com.aduilio.personapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aduilio.personapi.dto.PersonDTO;
import com.aduilio.personapi.entity.Person;
import com.aduilio.personapi.mapper.PersonMapper;
import com.aduilio.personapi.repository.PersonRepository;

@Service
public class PersonService {

	private final PersonRepository personRepository;

	PersonMapper personMapper = PersonMapper.INSTANCE;

	@Autowired
	public PersonService(final PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	public Long create(final PersonDTO personDTO) {
		return personRepository.save(personMapper.mapPersonFrom(personDTO))
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
