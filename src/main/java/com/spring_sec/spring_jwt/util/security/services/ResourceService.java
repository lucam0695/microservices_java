package com.spring_sec.spring_jwt.util.security.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.spring_sec.spring_jwt.model.Resource;
import com.spring_sec.spring_jwt.repository.ResourceRepository;


@Service
public class ResourceService {
    @Autowired
    ResourceRepository resourceRepository;

    public Page<Resource> findAll(Pageable pageable){
        return resourceRepository.findAll(pageable);
    }

    public Optional<Resource> findById(Long id) {
		return resourceRepository.findById(id);
	}

    public Resource update(Long id, Resource resource){
        Optional<Resource> resourceResult = resourceRepository.findById(id);
        if (resourceResult.isPresent()) {
            Resource ressourceUpdate = resourceResult.get();
            ressourceUpdate.setType(resource.getType());
            ressourceUpdate.setUrl(resource.getUrl());
            resourceRepository.save(ressourceUpdate);
            return ressourceUpdate;
        } else{
			throw new Error("Failed to update resource");
        }
    }

    public Resource save(Resource resource) {
		return resourceRepository.save(resource);
	}

    public void delete(Long id) {
		resourceRepository.deleteById(id);
	}
}