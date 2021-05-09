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

	/**
	 * Create a person.
	 *
	 * @param personDTO to be created
	 *
	 * @return the id the person created
	 */
	public Long create(final PersonDTO personDTO) {
		return savePerson(personDTO).getId();

	}

	/**
	 * Read a person by id.
	 * 
	 * @param id of the Person
	 * 
	 * @return {@link PersonDTO}
	 * 
	 * @throws PersonNotFoundException if the id does not exist
	 */
	public PersonDTO read(final Long id) throws PersonNotFoundException {
		return personMapper.mapPersonDTOFrom(readPerson(id));
	}

	/**
	 * Read all people.
	 *
	 * @return {@link List} of {@link PersonDTO}
	 */
	public List<PersonDTO> list() {
		return personRepository.findAll()
				.stream()
				.map(personMapper::mapPersonDTOFrom)
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
	 * @param personDTO to be updated
	 * 
	 * @return {@link PersonDTO} with the new values
	 * 
	 * @throws PersonNotFoundException if the id does not exist
	 */
	public PersonDTO update(final Long id, final PersonDTO personDTO) throws PersonNotFoundException {
		readPerson(id);

		personDTO.setId(id);
		return personMapper.mapPersonDTOFrom(savePerson(personDTO));

	}

	private Person readPerson(final Long id) throws PersonNotFoundException {
		return personRepository.findById(id)
				.orElseThrow(() -> new PersonNotFoundException(id));
	}

	private Person savePerson(final PersonDTO personDTO) {
		return personRepository.save(personMapper.mapPersonFrom(personDTO));
	}
}
