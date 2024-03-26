package com.spring_sec.spring_jwt.util.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.spring_sec.spring_jwt.model.FollowersFollowing;
import com.spring_sec.spring_jwt.repository.FollowersFollowingRepository;


@Service
public class FollowersFollowingService {
    @Autowired
    FollowersFollowingRepository followersFollowingRepository;

    public Page<FollowersFollowing> findAll(Pageable pageable){
        return followersFollowingRepository.findAll(pageable);
    }
}