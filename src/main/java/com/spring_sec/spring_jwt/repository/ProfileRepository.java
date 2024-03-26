package com.spring_sec.spring_jwt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring_sec.spring_jwt.model.Profile;
import com.spring_sec.spring_jwt.model.UserInstabasic;

public interface ProfileRepository extends JpaRepository<Profile, Long>{
    List<Profile> findAll();
    List<Profile> findByUser(UserInstabasic user);

}
