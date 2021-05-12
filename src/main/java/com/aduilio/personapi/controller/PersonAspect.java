package com.aduilio.personapi.controller;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;

import com.aduilio.personapi.dto.PersonDto;

@Aspect
@Configuration
public class PersonAspect {

	@Before("execution(* com.aduilio.personapi.controller.PersonController.update2(..)) && args(id, personDto)")
	public void validate(final JoinPoint joinPoint, final Long id, final PersonDto personDto) {

		personDto.setId(null);
	}
}
