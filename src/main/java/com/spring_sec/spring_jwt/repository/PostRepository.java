package com.spring_sec.spring_jwt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring_sec.spring_jwt.model.Post;
import com.spring_sec.spring_jwt.model.Profile;


public interface PostRepository extends JpaRepository<Post, Long>{
    List<Post> findAll();
    List<Post> findByProfile(Profile profile);
}
