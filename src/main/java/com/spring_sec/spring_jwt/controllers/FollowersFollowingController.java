package com.spring_sec.spring_jwt.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring_sec.spring_jwt.model.FollowersFollowing;
import com.spring_sec.spring_jwt.util.security.services.FollowersFollowingService;



@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class FollowersFollowingController {
    @Autowired
    private FollowersFollowingService followersFollowingService;

    @GetMapping(path = "/followerFollowing")
      public ResponseEntity<Page<FollowersFollowing>> findAll(Pageable pageable) {
        Page<FollowersFollowing> findAll = followersFollowingService.findAll(pageable);

        if (findAll.hasContent()) {
            return new ResponseEntity<>(findAll, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
