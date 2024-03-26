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

import com.spring_sec.spring_jwt.model.Resource;
import com.spring_sec.spring_jwt.util.security.services.ResourceService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class ResourceController {
    @Autowired
    private ResourceService resourceService;

    @GetMapping(path = "/resource")
      public ResponseEntity<Page<Resource>> findAll(Pageable pageable) {
        Page<Resource> findAll = resourceService.findAll(pageable);

        if (findAll.hasContent()) {
            return new ResponseEntity<>(findAll, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/resource/{id}")
  	public ResponseEntity<Resource> findById(@PathVariable(required = true) Long id) {
		Optional<Resource> find = resourceService.findById(id);

		if (find.isPresent()) {
			return new ResponseEntity<>(find.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

    @PostMapping(path = "/resource")
	public ResponseEntity<Resource> save(@RequestBody Resource resource) {
		Resource save = resourceService.save(resource);
		return new ResponseEntity<>(save, HttpStatus.OK);
	}

    @PutMapping(path = "/resource/{id}")
	public ResponseEntity<Resource> update(@PathVariable Long id, @RequestBody Resource resource) {
		Resource save = resourceService.update(id, resource);
		return new ResponseEntity<>(save, HttpStatus.OK);
	}

    @DeleteMapping(path = "/resource/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		resourceService.delete(id);
		return new ResponseEntity<>("Resource deleted", HttpStatus.OK);
	}

}
