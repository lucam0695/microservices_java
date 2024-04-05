package com.spring_sec.spring_ms.util.security.services;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring_sec.spring_ms.model.Role;
import com.spring_sec.spring_ms.model.Status;
import com.spring_sec.spring_ms.model.UserMs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {
  private static final long serialVersionUID = 1L;

  private Long id;

  private String username;

  private String email;

  @JsonIgnore
  private String password;

  private String role;

  private Status status;
  

  public UserDetailsImpl(Role userRole, Status userStatus, Long id, String username, String email,
  String password) {
    this.role = userRole.toString();
    this.status = userStatus;
    this.id = id;
    this.username = username;
    this.email = email;
    this.password = password;
  }

  public static UserDetailsImpl build(UserMs user) {
      return new UserDetailsImpl(
        user.getUserRole(),
        user.getUserStatus(),
        user.getId(),
        user.getUsername(),
        user.getEmail(),
        user.getPassword());
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singleton(new SimpleGrantedAuthority(role.toString()));
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;

    if (o == null || getClass() != o.getClass())
      return false;

    UserDetailsImpl user = (UserDetailsImpl) o;

    return Objects.equals(id, user.id);
  }


}