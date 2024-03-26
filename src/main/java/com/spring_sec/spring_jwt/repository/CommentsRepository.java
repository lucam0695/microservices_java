package com.spring_sec.spring_jwt.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring_sec.spring_jwt.model.Comments;


public interface CommentsRepository extends JpaRepository<Comments, Long>{
    List<Comments> findAll();
}

