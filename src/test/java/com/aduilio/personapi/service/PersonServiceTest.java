package com.aduilio.personapi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.aduilio.personapi.dto.PersonDto;
import com.aduilio.personapi.entity.Person;
import com.aduilio.personapi.exception.PersonNotFoundException;
import com.aduilio.personapi.repository.PersonRepository;
import com.aduilio.personapi.utils.PersonUtils;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

	@Mock
	private PersonRepository personRepositoryMock;

	@InjectMocks
	private PersonService personService;

	@Test
	void createWithValuesShouldReturnId() {
		final PersonDto personDto = PersonUtils.createPersonDTO();
		final Person person = PersonUtils.createPerson();

		when(personRepositoryMock.save(person)).thenReturn(person);

		final Long result = personService.create(personDto);

		assertThat(result).isEqualTo(person.getId());
	}

	@Test
	void readWithValidIdShouldReturnPerson() throws PersonNotFoundException {
		final PersonDto personDto = PersonUtils.createPersonDTO();
		final Person person = PersonUtils.createPerson();

		when(personRepositoryMock.findById(person.getId())).thenReturn(Optional.of(person));

		final PersonDto result = personService.read(PersonUtils.ID);

		assertThat(result).isEqualTo(personDto);
	}

	@Test
	void readWithInvalidIdShouldThrowException() throws PersonNotFoundException {
		final Person person = PersonUtils.createPerson();

		when(personRepositoryMock.findById(person.getId())).thenReturn(Optional.empty());

		assertThrows(PersonNotFoundException.class, () -> personService.read(PersonUtils.ID));
	}

	@Test
	void delateWithValidIdShouldExecute() throws PersonNotFoundException {
		final Person person = PersonUtils.createPerson();

		when(personRepositoryMock.findById(person.getId())).thenReturn(Optional.of(person));

		personService.delete(PersonUtils.ID);

		verify(personRepositoryMock, times(1)).findById(person.getId());
	}

	@Test
	void deleteWithInvalidIdShouldThrowException() throws PersonNotFoundException {
		final Person person = PersonUtils.createPerson();

		when(personRepositoryMock.findById(person.getId())).thenReturn(Optional.empty());

		assertThrows(PersonNotFoundException.class, () -> personService.delete(PersonUtils.ID));
	}

	@Test
	void updateWithValidIdShouldReturnPerson() throws PersonNotFoundException {
		final PersonDto personDto = PersonUtils.createPersonDTO();
		final PersonDto personDtoUpdate = PersonUtils.createPersonDTOUpdate();
		final Person person = PersonUtils.createPerson();
		final Person personUpdated = PersonUtils.createPersonUpdated();

		when(personRepositoryMock.findById(person.getId())).thenReturn(Optional.of(person));
		when(personRepositoryMock.save(person)).thenReturn(personUpdated);

		final PersonDto result = personService.update(personDto.getId(), personDto);

		assertThat(result).isEqualTo(personDtoUpdate);
		verify(personRepositoryMock, times(1)).findById(person.getId());
	}

	@Test
	void updateWithInvalidIdShouldThrowException() throws PersonNotFoundException {
		final PersonDto personDto = PersonUtils.createPersonDTO();

		when(personRepositoryMock.findById(personDto.getId())).thenReturn(Optional.empty());

		assertThrows(PersonNotFoundException.class, () -> personService.update(personDto.getId(), personDto));
	}
}
