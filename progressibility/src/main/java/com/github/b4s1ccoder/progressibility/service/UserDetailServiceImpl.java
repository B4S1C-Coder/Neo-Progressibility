package com.github.b4s1ccoder.progressibility.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.github.b4s1ccoder.progressibility.entity.User;
import com.github.b4s1ccoder.progressibility.repository.UserRepository;
import com.github.b4s1ccoder.progressibility.security.UserEntityIncludedUserDetails;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("Trying to find user by email: " + email);
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            log.info("User found with email: " + email);
            return new UserEntityIncludedUserDetails(user.get());
        }

        throw new UsernameNotFoundException("No user associated with email: "+ email);
    }
}
