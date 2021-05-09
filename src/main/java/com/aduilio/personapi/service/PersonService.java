package com.aduilio.personapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aduilio.personapi.dto.PersonDTO;
import com.aduilio.personapi.entity.Person;
import com.aduilio.personapi.exception.PersonNotFoundException;
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

	public PersonDTO read(final Long id) throws PersonNotFoundException {
		return personMapper.mapPersonDTOFrom(readPerson(id));
	}

	public List<PersonDTO> list() {
		return personRepository.findAll()
				.stream()
				.map(personMapper::mapPersonDTOFrom)
				.collect(Collectors.toList());

	}

	public void delete(final Long id) throws PersonNotFoundException {
		personRepository.delete(readPerson(id));
	}

	public void update(final Person person) {
		final Person save = personRepository.save(person);
	}

	private Person readPerson(final Long id) throws PersonNotFoundException {
		return personRepository.findById(id)
				.orElseThrow(() -> new PersonNotFoundException(id));
	}
}
