package com.aduilio.personapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.aduilio.personapi.dto.PersonDto;
import com.aduilio.personapi.dto.PersonResponseDto;
import com.aduilio.personapi.exception.PersonNotFoundException;
import com.aduilio.personapi.service.PersonService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/v1/people")
@AllArgsConstructor
public class PersonController {

	private final PersonService personService;

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public PersonResponseDto create(@RequestBody @Valid final PersonDto personDto) {
		final Long id = personService.create(personDto);

		return PersonResponseDto.builder()
				.id(id)
				.build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<PersonDto> read(@PathVariable final Long id) throws PersonNotFoundException {
		return ResponseEntity.ok(personService.read(id));
	}

	@GetMapping
	public ResponseEntity<List<PersonDto>> list() {
		return ResponseEntity.ok(personService.list());
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable final Long id) throws PersonNotFoundException {
		personService.delete(id);
	}

	@PutMapping("/{id}")
	public PersonDto update(@PathVariable final Long id, @RequestBody @Valid final PersonDto personDto)
			throws PersonNotFoundException {
		return personService.update(id, personDto);
	}
}
