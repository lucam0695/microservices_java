package com.spring_sec.spring_ms.model;


import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserMs {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String username;
  private String email;
  private String password;

  @Enumerated(EnumType.STRING)
  @Column(name = "Role")
  private Role userRole;

  private LocalDateTime blockedAt;

  public UserMs(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.userRole = Role.USER;
    this.blockedAt = null;
  }

}