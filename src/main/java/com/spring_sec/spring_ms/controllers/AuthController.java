package com.spring_sec.spring_ms.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring_sec.spring_ms.model.Status;
import com.spring_sec.spring_ms.model.UserMs;
import com.spring_sec.spring_ms.payload.request.LoginRequest;
import com.spring_sec.spring_ms.payload.request.SignupRequest;
import com.spring_sec.spring_ms.payload.response.JwtResponse;
import com.spring_sec.spring_ms.payload.response.MessageResponse;
import com.spring_sec.spring_ms.repository.UserRepository;
import com.spring_sec.spring_ms.util.security.jwt.JwtUtils;
import com.spring_sec.spring_ms.util.security.services.UserDetailsImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;


  @PostMapping(path = "/login")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    String username = loginRequest.getUserName();
    String password = loginRequest.getPassword();
    try {
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(username, password));
      SecurityContextHolder.getContext().setAuthentication(authentication);
      String jwt = jwtUtils.generateJwtToken(authentication);
      UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
      System.out.println(userDetails);
      if (userDetails.getStatus() != Status.FREE) {
        return new ResponseEntity<>("This account has been banned", HttpStatus.UNAUTHORIZED);
      }
      return ResponseEntity.ok(new JwtResponse(jwt,
        userDetails.getId(),
        userDetails.getUsername(),
        userDetails.getEmail()));
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }


  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUserName())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Username is already taken!"));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Email is already in use!"));
    }

    // Create new user's account
    UserMs user = new UserMs(signUpRequest.getUserName(),
        signUpRequest.getEmail(),
        encoder.encode(signUpRequest.getPassword()));
      userRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }
}
