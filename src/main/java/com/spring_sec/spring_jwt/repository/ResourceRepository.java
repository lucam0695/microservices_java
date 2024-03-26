package com.spring_sec.spring_jwt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring_sec.spring_jwt.model.Resource;


public interface ResourceRepository extends JpaRepository<Resource, Long>{
    List<Resource> findAll();
}
