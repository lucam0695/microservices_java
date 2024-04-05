package com.spring_sec.spring_ms.util.security.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring_sec.spring_ms.model.UserMs;
import com.spring_sec.spring_ms.repository.ProfileRepository;

@Service
public class ProfileService {
    @Autowired
    ProfileRepository profileRepository;

    public Optional<UserMs> findByUsername(String username) {
        return profileRepository.findByUsername(username);
    }

    public Optional<UserMs> findById(Long id) {
        return profileRepository.findById(id);
    }

    public UserMs update(Long id, UserMs user) {
        Optional<UserMs> userResult = profileRepository.findById(id);
        if (userResult.isPresent()) {
            UserMs userUpdate = userResult.get();
            userUpdate.setUsername(user.getUsername());
            userUpdate.setEmail(user.getEmail());
            profileRepository.save(userUpdate);
            return userUpdate;
        } else {
            throw new RuntimeException();
        }
    }

    public void delete(Long id) {
        profileRepository.deleteById(id);
    }
}
