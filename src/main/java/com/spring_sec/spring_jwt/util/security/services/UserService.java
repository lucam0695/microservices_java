package com.spring_sec.spring_jwt.util.security.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.spring_sec.spring_jwt.model.UserInstabasic;
import com.spring_sec.spring_jwt.repository.UserRepository;



@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public Page<UserInstabasic> findAll(Pageable pageable){
        return userRepository.findAll(pageable);
    }

    public Optional<UserInstabasic> findById(Long id) {
		return userRepository.findById(id);
	}

    public UserInstabasic save(UserInstabasic user) {
		return userRepository.save(user);
	}

    public UserInstabasic update(Long id, UserInstabasic user){
        Optional<UserInstabasic> userResult = userRepository.findById(id);
        if (userResult.isPresent()) {
            UserInstabasic userUpdate = userResult.get();
            userUpdate.setUserName(user.getUserName());
            userUpdate.setEmail(user.getEmail());
            userRepository.save(userUpdate);
            return userUpdate;
        } else{
            throw new RuntimeException();
        }
    }
    
    public void delete(Long id) {
		userRepository.deleteById(id);
	}
}
