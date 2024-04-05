package com.spring_sec.spring_ms.payload.response;

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

  public JwtResponse(String token, Long id, String userName, String email) {
    this.token = token;
    this.id = id;
    this.userName = userName;
    this.email = email;
  }

}
