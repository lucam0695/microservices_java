package com.spring_sec.spring_jwt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring_sec.spring_jwt.model.UserInstabasic;


public interface UserRepository extends JpaRepository<UserInstabasic, Long>{
    List<UserInstabasic> findAll();
    
    Boolean existsByUserName(String userName);
    Boolean existsByEmail(String email);
    Optional<UserInstabasic> findByUserName(String userName);

}
