package com.spring_sec.spring_jwt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring_sec.spring_jwt.model.FollowersFollowing;


public interface FollowersFollowingRepository extends JpaRepository<FollowersFollowing, Long>{
    List<FollowersFollowing> findAll();
}
