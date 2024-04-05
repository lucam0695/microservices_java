package com.spring_sec.spring_ms.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring_sec.spring_ms.model.UserMs;
import com.spring_sec.spring_ms.util.security.services.UserService;


@RestController
@RequestMapping("/api")
@Secured("ADMIN") // annotation per abilitare la rotta solo agli admin
@CrossOrigin(origins = "")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(path = "/user")
      public ResponseEntity<Page<UserMs>> findAll(Pageable pageable) {
        Page<UserMs> findAll = userService.findAll(pageable);

        if (findAll.hasContent()) {
            return new ResponseEntity<>(findAll, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/user/{id}")
    public ResponseEntity<UserMs> findById(@PathVariable(required = true) Long id){

        Optional<UserMs> user = userService.findById(id);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/user")
      public ResponseEntity<UserMs> save(@RequestBody UserMs user ) {
        UserMs save = userService.save(user);
		return new ResponseEntity<>(save, HttpStatus.OK);
    }

    @PutMapping(path = "/user/{id}")
    public ResponseEntity<UserMs> update(@PathVariable Long id, @RequestBody UserMs user ){
        UserMs userUpdated = userService.update(id, user);
		return new ResponseEntity<>(userUpdated, HttpStatus.OK);
    }

    @PutMapping(path = "/user/status/{id}")
    public ResponseEntity<UserMs> changeStatus(@PathVariable Long id, @RequestBody UserMs user ){
        UserMs userUpdated = userService.changeStatus(id, user);
		return new ResponseEntity<>(userUpdated, HttpStatus.OK);
    }

    @DeleteMapping(path = "/user/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        userService.delete(id);
		return new ResponseEntity<>("User deleted", HttpStatus.OK);
    }
}