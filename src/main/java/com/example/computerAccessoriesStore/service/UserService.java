package com.example.computerAccessoriesStore.service;

import com.example.computerAccessoriesStore.entity.enums.Roles;
import com.example.computerAccessoriesStore.entity.User;
import com.example.computerAccessoriesStore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.computerAccessoriesStore.config.SecurityConfig;

import java.util.Collections;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserService
{

    @Autowired
    private UserRepository userRepository;

    public boolean addUserToDataBase(User user)
    {
        if(userRepository.findByEmail(user.getEmail()) != null) return false;
        user.setActive(true);
        user.setPassword(SecurityConfig.passwordEncoder().encode(user.getPassword()));
        user.setRole(Collections.singleton(Roles.ADMIN));
        log.info("saving user");
        userRepository.save(user);
        return true;
    }

    public void setUserRole(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id " + userId));
        if (user != null) {
            user.setRole(Collections.singleton(Roles.USER));
            userRepository.save(user);
        }
    }

    public void deleteUser(long userId)
    {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id " + userId));
        if (user != null) {
            userRepository.delete(user);
        }
    }

    public User getUserById(Long userId)
    {
        return userRepository.findById(userId).get();
    }

}
