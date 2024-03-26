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

import com.spring_sec.spring_jwt.model.Comments;
import com.spring_sec.spring_jwt.util.security.services.CommentsService;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class CommentsController {
    @Autowired
    private CommentsService commentsService;

    @GetMapping(path = "/comments")
    public ResponseEntity<Page<Comments>> findAll(Pageable pageable) {
        Page<Comments> findAll = commentsService.findAll(pageable);

        if (findAll.hasContent()) {
            return new ResponseEntity<>(findAll, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/comments/{id}")
    public ResponseEntity<Comments> findById(@PathVariable(required = true) Long id) {
        Optional<Comments> comment = commentsService.findById(id);
        if (comment.isPresent()) {
            return new ResponseEntity<>(comment.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/comments")
    public ResponseEntity<Comments> save(@RequestBody Comments comment) {
        Comments save = commentsService.save(comment);
        return new ResponseEntity<>(save, HttpStatus.OK);
    }

    @PutMapping(path = "/comments/{id}")
    public ResponseEntity<Comments> update(@PathVariable Long id, @RequestBody Comments comment) {
        Comments commentUpdate = commentsService.update(id, comment);
        return new ResponseEntity<>(commentUpdate, HttpStatus.OK);
    }

    @DeleteMapping(path = "/comments/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        commentsService.delete(id);
        return new ResponseEntity<>("User deleted", HttpStatus.OK);
    }
}
