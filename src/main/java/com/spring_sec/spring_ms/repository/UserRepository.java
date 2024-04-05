package com.spring_sec.spring_ms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring_sec.spring_ms.model.UserMs;



public interface UserRepository extends JpaRepository<UserMs, Long>{
    List<UserMs> findAll();
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Optional<UserMs> findByUsername(String username);

}
