package com.spring_sec.spring_ms.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring_sec.spring_ms.model.UserMs;
import com.spring_sec.spring_ms.util.security.services.ProfileService;
import com.spring_sec.spring_ms.util.security.services.UserDetailsImpl;
import com.spring_sec.spring_ms.util.security.services.UserService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private UserService userService;

    @GetMapping(path = "/profile")
    public ResponseEntity<UserMs> profile() {

        UserDetailsImpl authentication = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Long currentId = authentication.getId();
        Optional<UserMs> user = profileService.findById(currentId);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping(path = "/profile")
    public ResponseEntity<UserMs> update(@RequestBody UserMs user) {
        UserDetailsImpl authentication = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Long currentId = authentication.getId();
        UserMs userUpdated = profileService.update(currentId, user);
        System.out.println(userUpdated);
        return new ResponseEntity<>(userUpdated, HttpStatus.OK);
    }


    @DeleteMapping(path = "/profile")
    public ResponseEntity<String> delete() {
        UserDetailsImpl authentication = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Long currentId = authentication.getId();
        userService.delete(currentId);
        return new ResponseEntity<>("User deleted", HttpStatus.OK);
    }
}
