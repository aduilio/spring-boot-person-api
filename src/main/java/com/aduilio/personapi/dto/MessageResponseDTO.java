package com.aduilio.personapi.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MessageResponseDTO {

	private final String message;
}
