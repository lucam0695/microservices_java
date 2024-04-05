package com.spring_sec.spring_ms.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class LoginRequest {
	@NotBlank
	private String userName;

	@NotBlank
	private String password;

}
