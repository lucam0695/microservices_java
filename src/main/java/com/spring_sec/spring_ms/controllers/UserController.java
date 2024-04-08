package com.spring_sec.spring_ms.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

import com.spring_sec.spring_ms.model.UserMs;
import com.spring_sec.spring_ms.util.security.services.UserDetailsImpl;
import com.spring_sec.spring_ms.util.security.services.UserService;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "")
@Secured("ADMIN") // annotation per abilitare la rotta solo agli admin
public class UserController {
    @Autowired
    private UserService userService;

    private final ObjectMapper objectMapper = new ObjectMapper();


    private UserMs applyPatchToUser(JsonPatch patch, UserMs targetUser) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(targetUser, JsonNode.class));
        return objectMapper.treeToValue(patched, UserMs.class);
    }

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

    @PatchMapping(path = "/user", consumes = "application/json-patch+json")
    public ResponseEntity<?> updatePatch(@RequestBody JsonPatch patch) {
        UserDetailsImpl authentication = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Long currentId = authentication.getId();
        try {
            UserMs user;
            Optional<UserMs> userResult = userService.findById(currentId);
            if (userResult.isPresent()) {
                user = userResult.get();
            } else{
                return ResponseEntity.badRequest().body("User not found!");
            }
            UserMs userPatched = applyPatchToUser(patch, user);
            userService.update(currentId, userPatched);
            return ResponseEntity.ok(userPatched);
            
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
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