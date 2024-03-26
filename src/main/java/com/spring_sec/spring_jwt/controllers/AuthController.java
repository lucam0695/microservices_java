package com.spring_sec.spring_jwt.controllers;

import java.time.LocalDateTime;

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

import com.spring_sec.spring_jwt.model.Profile;
import com.spring_sec.spring_jwt.model.UserInstabasic;
import com.spring_sec.spring_jwt.payload.request.LoginRequest;
import com.spring_sec.spring_jwt.payload.request.SignupRequest;
import com.spring_sec.spring_jwt.payload.response.JwtResponse;
import com.spring_sec.spring_jwt.payload.response.MessageResponse;
import com.spring_sec.spring_jwt.repository.ProfileRepository;
import com.spring_sec.spring_jwt.repository.UserRepository;
import com.spring_sec.spring_jwt.util.security.jwt.JwtUtils;
import com.spring_sec.spring_jwt.util.security.services.UserDetailsImpl;

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
  ProfileRepository profileRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @PostMapping(path = "/login")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    String userName = loginRequest.getUserName();
    String password = loginRequest.getPassword();
    try {
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(userName, password));
      SecurityContextHolder.getContext().setAuthentication(authentication);
      String jwt = jwtUtils.generateJwtToken(authentication);
      UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
      return ResponseEntity.ok(new JwtResponse(jwt,
        userDetails.getId(),
        userDetails.getUsername(),
        userDetails.getEmail(), null));
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return new ResponseEntity(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }
  }


  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    System.out.println("richiesta ricevuta");
    if (userRepository.existsByUserName(signUpRequest.getUserName())) {
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
    UserInstabasic user = new UserInstabasic(signUpRequest.getUserName(),
        signUpRequest.getEmail(),
        encoder.encode(signUpRequest.getPassword()), 
        LocalDateTime.now());
        
    Profile defaultProfile = new Profile();
    defaultProfile.setUserName(user.getUserName());
    defaultProfile.setUser(user);
    
    userRepository.save(user);
    profileRepository.save(defaultProfile);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }
}
