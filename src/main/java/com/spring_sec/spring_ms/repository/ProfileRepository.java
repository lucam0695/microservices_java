package com.spring_sec.spring_ms.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


import com.spring_sec.spring_ms.model.UserMs;

public interface ProfileRepository extends JpaRepository<UserMs, Long>{
    Optional<UserMs> findByUsername(String username);
    Optional<UserMs> findById(Long id);


}
