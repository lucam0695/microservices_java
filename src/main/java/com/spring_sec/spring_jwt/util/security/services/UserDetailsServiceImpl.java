package com.spring_sec.spring_jwt.util.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring_sec.spring_jwt.model.UserInstabasic;
import com.spring_sec.spring_jwt.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  UserRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
    UserInstabasic user = userRepository.findByUserName(userName)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + userName));

    return UserDetailsImpl.build(user);
  }

}
