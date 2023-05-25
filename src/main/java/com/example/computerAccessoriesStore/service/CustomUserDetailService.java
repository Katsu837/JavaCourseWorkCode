package com.example.computerAccessoriesStore.service;

import com.example.computerAccessoriesStore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public class CustomUserDetailService implements UserDetailsService {
     @Autowired
     private UserRepository userRepository;
     @Override
     public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
         return (UserDetails) userRepository.findByEmail(email);
     }
}

