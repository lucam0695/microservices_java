package com.spring_sec.spring_ms.payload.request;

import com.spring_sec.spring_ms.model.Role;

import lombok.AllArgsConstructor;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;

@Data
@AllArgsConstructor
public class SignupRequest {
  @NotBlank
  @Size(min = 3, max = 20)
  private String userName;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  private Role role;

  @NotBlank
  @Size(min = 6, max = 40)
  private String password;
}
