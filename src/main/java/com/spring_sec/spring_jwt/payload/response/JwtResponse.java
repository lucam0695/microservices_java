package com.spring_sec.spring_jwt.payload.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {

  private String token;
  private String type = "Bearer";
  private Long id;
  private String userName;
  private String email;
  private List<String> roles;

  public JwtResponse(String token, Long id, String userName, String email, List<String> roles) {
    this.token = token;
    this.id = id;
    this.userName = userName;
    this.email = email;
    this.roles = roles;
  }

}
