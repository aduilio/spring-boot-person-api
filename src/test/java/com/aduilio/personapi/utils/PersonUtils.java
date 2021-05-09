package com.aduilio.personapi.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

import com.aduilio.personapi.dto.PersonDto;
import com.aduilio.personapi.dto.PhoneDto;
import com.aduilio.personapi.entity.Person;
import com.aduilio.personapi.entity.Phone;
import com.aduilio.personapi.enums.PhoneType;

public class PersonUtils {

	public static final Long ID = 1L;
	public static final String FIRST_NAME = "fName";
	public static final String LAST_NAME = "lName";
	public static final String BIRTH_DATE_STR = "01-01-2000";
	public static final String CPF = "298.218.080-42";
	public static final LocalDate BIRTH_DATE_LDATE = LocalDate.parse(BIRTH_DATE_STR,
			DateTimeFormatter.ofPattern("dd-MM-yyyy"));

	public static final String PHONE_NUMBER = "(12) 3456-7890";
	public static final PhoneType PHONE_TYPE = PhoneType.MOBILE;

	public static Person createPerson() {
		return Person.builder()
				.id(ID)
				.firstName(FIRST_NAME)
				.lastName(LAST_NAME)
				.birthDate(BIRTH_DATE_LDATE)
				.cpf(CPF)
				.phones(Collections.singletonList(Phone.builder()
						.number(PHONE_NUMBER)
						.type(PHONE_TYPE)
						.build()))
				.build();
	}

	public static Person createPersonUpdated() {
		return Person.builder()
				.id(ID)
				.firstName("New" + FIRST_NAME)
				.lastName("New" + LAST_NAME)
				.birthDate(BIRTH_DATE_LDATE)
				.cpf(CPF)
				.phones(Collections.singletonList(Phone.builder()
						.number(PHONE_NUMBER)
						.type(PHONE_TYPE)
						.build()))
				.build();
	}

	public static PersonDto createPersonDTO() {
		return PersonDto.builder()
				.id(ID)
				.firstName(FIRST_NAME)
				.lastName(LAST_NAME)
				.birthDate(BIRTH_DATE_STR)
				.cpf(CPF)
				.phones(Collections.singletonList(PhoneDto.builder()
						.number(PHONE_NUMBER)
						.type(PHONE_TYPE)
						.build()))
				.build();
	}

	public static PersonDto createPersonDTOUpdate() {
		return PersonDto.builder()
				.id(ID)
				.firstName("New" + FIRST_NAME)
				.lastName("New" + LAST_NAME)
				.birthDate(BIRTH_DATE_STR)
				.cpf(CPF)
				.phones(Collections.singletonList(PhoneDto.builder()
						.number(PHONE_NUMBER)
						.type(PHONE_TYPE)
						.build()))
				.build();
	}

}
