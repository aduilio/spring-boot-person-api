package com.aduilio.personapi.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.aduilio.personapi.dto.PersonDto;
import com.aduilio.personapi.service.PersonService;
import com.aduilio.personapi.utils.PersonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class PersonControllerTest {

	private static final String URL = "/api/v1/people";

	private MockMvc mockMvc;

	@Mock
	private PersonService personService;

	@InjectMocks
	private PersonController personController;

	@BeforeEach
	void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(personController)
				.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
				.setViewResolvers((viewName, locale) -> new MappingJackson2JsonView())
				.build();
	}

	@Test
	void createWithValidPayloadShouldReturnId() throws Exception {
		final PersonDto personDto = PersonUtils.createPersonDTO();

		when(personService.create(personDto)).thenReturn(personDto.getId());

		mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(personDto))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").isNotEmpty());
	}

	@Test
	void createWithInvalidCpfPayloadShouldReturnError() throws Exception {
		final PersonDto personDto = PersonUtils.createPersonDTO();
		personDto.setCpf("123");

		mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(personDto))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	void readWithValidId() throws Exception {
		final PersonDto personDto = PersonUtils.createPersonDTO();

		when(personService.read(personDto.getId())).thenReturn(personDto);

		mockMvc.perform(get(URL + "/" + personDto.getId()).accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", is(personDto.getFirstName())));
	}

	@Test
	void listWithValues() throws Exception {
		final PersonDto personDto = PersonUtils.createPersonDTO();

		when(personService.list()).thenReturn(Collections.singletonList(personDto));

		mockMvc.perform(get(URL).accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].firstName", is(personDto.getFirstName())));
	}

	@Test
	void deleteWithValidId() throws Exception {
		final PersonDto personDto = PersonUtils.createPersonDTO();

		mockMvc.perform(delete(URL + "/" + personDto.getId()).accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isNoContent());
	}

	@Test
	void updateWithValidPayload() throws Exception {
		final PersonDto personDto = PersonUtils.createPersonDTO();

		when(personService.update(personDto.getId(), personDto)).thenReturn(personDto);

		mockMvc.perform(put(URL + "/" + personDto.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(personDto))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").isNotEmpty());
	}
}
