package com.spring_sec.spring_jwt.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
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
public class UserInstabasic{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String userName;
    private String email;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime uptatedAt;
    private LocalDateTime lastLoginAt;
    private LocalDateTime lastOnlineAt;

    public UserInstabasic(String userName, String email, String password, LocalDateTime createdAt) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
      }


}   