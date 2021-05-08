package com.aduilio.personapi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.aduilio.personapi.dto.PersonResponseDTO;
import com.aduilio.personapi.entity.Person;
import com.aduilio.personapi.service.PersonService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/v1/people")
@AllArgsConstructor
public class PersonController {

	private final PersonService personService;

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public PersonResponseDTO create(@RequestBody final Person person) {
		final Long id = personService.create(person);

		return PersonResponseDTO.builder()
				.id(id)
				.build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Person> read(@PathVariable final Long id) {
		final Optional<Person> person = personService.read(id);

		if (person.isPresent()) {
			return ResponseEntity.ok(person.get());
		}

		return ResponseEntity.notFound()
				.build();
	}

	@GetMapping
	public ResponseEntity<List<Person>> list() {
		final List<Person> people = personService.list();

		return ResponseEntity.ok(people);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable final Long id) {
		personService.delete(id);
	}
}
