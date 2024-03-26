package com.spring_sec.spring_jwt.controllers;

import java.util.List;
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
import com.spring_sec.spring_jwt.model.Profile;
import com.spring_sec.spring_jwt.model.UserInstabasic;
import com.spring_sec.spring_jwt.util.security.services.ProfileService;
import com.spring_sec.spring_jwt.util.security.services.UserService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @Autowired
    private UserService userService;


    @GetMapping(path = "profile/user/{id}")
    public ResponseEntity<?> findByUser(@PathVariable(required = true) Long id){
        Optional<UserInstabasic> user = userService.findById(id);
        if (user.isPresent()) {
            List<Profile> profiles = profileService.findByUser(user.get());
            if (!profiles.isEmpty()) {
                return ResponseEntity.ok(profiles);
            } else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Profiles not found");
            }
        } else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Users not found");
        }
    }

    @GetMapping(path = "/profile")
      public ResponseEntity<Page<Profile>> findAll(Pageable pageable) {
        Page<Profile> findAll = profileService.findAll(pageable);

        if (findAll.hasContent()) {
            return new ResponseEntity<>(findAll, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/profile/{id}")
    public ResponseEntity<Profile> findById(@PathVariable(required = true) Long id){
        Optional<Profile> find = profileService.findById(id);
        if (find.isPresent()) {
            return new ResponseEntity<>(find.get(), HttpStatus.OK);
        } else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping(path = "/profile")
    public ResponseEntity<Profile> save(@RequestBody Profile profile) {
        Profile save = profileService.save(profile);
        return new ResponseEntity<>(save, HttpStatus.OK);
    }

    @PutMapping(path = "/profile/{id}")
    public ResponseEntity<Profile> update(@PathVariable Long id, @RequestBody Profile profile){
        Profile save = profileService.update(id, profile);
		return new ResponseEntity<>(save, HttpStatus.OK);
    }

    @DeleteMapping(path = "/profile/{id}")
    ResponseEntity<String> delete(@PathVariable Long id) {
		profileService.delete(id);
		return new ResponseEntity<>("Postazione deleted", HttpStatus.OK);
    }
}

