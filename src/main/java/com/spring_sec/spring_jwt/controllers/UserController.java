package com.spring_sec.spring_jwt.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring_sec.spring_jwt.model.UserInstabasic;
import com.spring_sec.spring_jwt.util.security.services.UserService;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(path = "/user")
      public ResponseEntity<Page<UserInstabasic>> findAll(Pageable pageable) {
        Page<UserInstabasic> findAll = userService.findAll(pageable);

        if (findAll.hasContent()) {
            return new ResponseEntity<>(findAll, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/user/{id}")
    public ResponseEntity<UserInstabasic> findById(@PathVariable(required = true) Long id){
        Optional<UserInstabasic> user = userService.findById(id);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    //In teoria non dovrei creare nuovi user da qui perchè vengono creati alla registrazione
    @PostMapping(path = "/user")
      public ResponseEntity<UserInstabasic> save(@RequestBody UserInstabasic user ) {
        UserInstabasic save = userService.save(user);
		return new ResponseEntity<>(save, HttpStatus.OK);
    }

    @PutMapping(path = "/user/{id}")
    public ResponseEntity<UserInstabasic> update(@PathVariable Long id, @RequestBody UserInstabasic user ){
        UserInstabasic userUpdated = userService.update(id, user);
		return new ResponseEntity<>(userUpdated, HttpStatus.OK);
    }

    @DeleteMapping(path = "/user/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        userService.delete(id);
		return new ResponseEntity<>("User deleted", HttpStatus.OK);
    }
}