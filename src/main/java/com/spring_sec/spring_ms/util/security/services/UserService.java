package com.spring_sec.spring_ms.util.security.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.spring_sec.spring_ms.model.UserMs;
import com.spring_sec.spring_ms.repository.UserRepository;



@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public Page<UserMs> findAll(Pageable pageable){
        return userRepository.findAll(pageable);
    }

    public Optional<UserMs> findById(Long id) {
		return userRepository.findById(id);
	}

    public UserMs save(UserMs user) {
		return userRepository.save(user);
	}

    public UserMs update(Long id, UserMs user){
        Optional<UserMs> userResult = userRepository.findById(id);
        if (userResult.isPresent()) {
            UserMs userUpdate = userResult.get();
            userUpdate.setUsername(user.getUsername());
            userUpdate.setEmail(user.getEmail());
            userRepository.save(userUpdate);
            return userUpdate;
        } else{
            throw new RuntimeException();
        }
    }

    public UserMs blockUnblockUsers(Long id){
        Optional<UserMs> userResult = userRepository.findById(id);
        if (userResult.isPresent()) {
            UserMs userUpdated = userResult.get();
            if(userUpdated.getBlockedAt() != null){
                userUpdated.setBlockedAt(null);
            } else {
                userUpdated.setBlockedAt(LocalDateTime.now());
            }
            userRepository.save(userUpdated);
            return userUpdated;
        } else{
            throw new RuntimeException();
        }
    }
    
    public void delete(Long id) {
		userRepository.deleteById(id);
	}
}
