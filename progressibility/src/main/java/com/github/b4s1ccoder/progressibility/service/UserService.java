package com.github.b4s1ccoder.progressibility.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.b4s1ccoder.progressibility.entity.User;
import com.github.b4s1ccoder.progressibility.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public User save(User user, boolean runPasswordEncoder) {
        if (runPasswordEncoder == true) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }
        return userRepository.save(user);
    }

    public String passwordEncode(String payload) {
        return passwordEncoder.encode(payload);
    }
}
