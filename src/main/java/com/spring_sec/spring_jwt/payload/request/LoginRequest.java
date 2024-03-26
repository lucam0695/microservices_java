package com.spring_sec.spring_jwt.payload.request;

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
