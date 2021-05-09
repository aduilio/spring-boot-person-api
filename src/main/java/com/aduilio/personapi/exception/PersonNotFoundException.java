package com.aduilio.personapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PersonNotFoundException extends Exception {

	private static final long serialVersionUID = 7980208164179909508L;

	public PersonNotFoundException(final Long id) {
		super("Person not found with id " + id);
	}

}
