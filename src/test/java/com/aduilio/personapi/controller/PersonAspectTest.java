package com.aduilio.personapi.controller;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.aduilio.personapi.dto.PersonDto;
import com.aduilio.personapi.exception.PersonNotFoundException;
import com.aduilio.personapi.service.PersonService;

@SpringBootTest
class PersonAspectTest {

	@Mock
	private PersonService mockPersonService;

	private PersonController personController;

	@BeforeEach
	public void setup() {
		personController = new PersonController(mockPersonService);
	}

	@Test
	public void invokeAOPStuff() throws PersonNotFoundException {
		final PersonDto personDto = new PersonDto();
		when(mockPersonService.update(1L, personDto)).thenReturn(personDto);

		final PersonDto s = personController.update(1L, new PersonDto());

	}

}
