package com.aduilio.personapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aduilio.personapi.dto.PersonDto;
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

	/**
	 * Create a person.
	 *
	 * @param personDto to be created
	 *
	 * @return the id the person created
	 */
	public Long create(final PersonDto personDto) {
		return savePerson(personDto).getId();

	}

	/**
	 * Read a person by id.
	 * 
	 * @param id of the Person
	 * 
	 * @return {@link PersonDto}
	 * 
	 * @throws PersonNotFoundException if the id does not exist
	 */
	public PersonDto read(final Long id) throws PersonNotFoundException {
		return personMapper.mapPersonDtoFrom(readPerson(id));
	}

	/**
	 * Read all people.
	 *
	 * @return {@link List} of {@link PersonDto}
	 */
	public List<PersonDto> list() {
		return personRepository.findAll()
				.stream()
				.map(personMapper::mapPersonDtoFrom)
				.collect(Collectors.toList());

	}

	/**
	 * Delete a Person by id.
	 *
	 * @param id of the Person
	 *
	 * @throws PersonNotFoundException if the id does not exist
	 */
	public void delete(final Long id) throws PersonNotFoundException {
		personRepository.delete(readPerson(id));
	}

	/**
	 * Update a person.
	 * 
	 * @param id        of the Person
	 * @param personDto to be updated
	 * 
	 * @return {@link PersonDto} with the new values
	 * 
	 * @throws PersonNotFoundException if the id does not exist
	 */
	public PersonDto update(final Long id, final PersonDto personDto) throws PersonNotFoundException {
		readPerson(id);

		personDto.setId(id);
		return personMapper.mapPersonDtoFrom(savePerson(personDto));

	}

	private Person readPerson(final Long id) throws PersonNotFoundException {
		return personRepository.findById(id)
				.orElseThrow(() -> new PersonNotFoundException(id));
	}

	private Person savePerson(final PersonDto personDto) {
		return personRepository.save(personMapper.mapPersonFrom(personDto));
	}
}
