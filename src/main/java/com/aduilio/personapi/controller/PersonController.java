package com.aduilio.personapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.aduilio.personapi.dto.PersonDTO;
import com.aduilio.personapi.dto.PersonResponseDTO;
import com.aduilio.personapi.entity.Person;
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
	public PersonResponseDTO create(@RequestBody @Valid final PersonDTO personDTO) {
		final Long id = personService.create(personDTO);

		return PersonResponseDTO.builder()
				.id(id)
				.build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<PersonDTO> read(@PathVariable final Long id) throws PersonNotFoundException {
		return ResponseEntity.ok(personService.read(id));
	}

	@GetMapping
	public ResponseEntity<List<PersonDTO>> list() {
		return ResponseEntity.ok(personService.list());
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable final Long id) throws PersonNotFoundException {
		personService.delete(id);
	}

	@PatchMapping("/{id}")
	public void update(@PathVariable final Long id, @RequestBody final Person person) {
		person.setId(id);
		personService.update(person);
	}
}
