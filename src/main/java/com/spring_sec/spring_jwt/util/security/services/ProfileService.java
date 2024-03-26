package com.spring_sec.spring_jwt.util.security.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.spring_sec.spring_jwt.model.Profile;
import com.spring_sec.spring_jwt.model.UserInstabasic;
import com.spring_sec.spring_jwt.repository.ProfileRepository;



@Service
public class ProfileService {
    @Autowired
    ProfileRepository profileRepository;

    public Page<Profile> findAll(Pageable pageable){
        return profileRepository.findAll(pageable);
    }

    public Optional<Profile> findById(Long id) {
		return profileRepository.findById(id);
	}

    public Profile save(Profile profile) {
		return profileRepository.save(profile);
	}

    public Profile update(Long id, Profile profile){
        Optional<Profile> profileResult = profileRepository.findById(id);
        if (profileResult.isPresent()) {
            Profile profileUpdate = profileResult.get();
            profileUpdate.setAvatarUrl(profile.getAvatarUrl());
            profileUpdate.setBio(profile.getBio());
            profileUpdate.setBirthDate(profile.getBirthDate());
            profileUpdate.setFirstName(profile.getFirstName());
            profileUpdate.setLastName(profile.getLastName());
            profileUpdate.setUserName(profile.getUserName());
            profileRepository.save(profileUpdate);
            return profileUpdate;
        } else{
            throw new RuntimeException();
        }
    }
    
    public void delete(Long id) {
		profileRepository.deleteById(id);
	}

    public List<Profile> findByUser(UserInstabasic user){
        return profileRepository.findByUser(user);
    }
}