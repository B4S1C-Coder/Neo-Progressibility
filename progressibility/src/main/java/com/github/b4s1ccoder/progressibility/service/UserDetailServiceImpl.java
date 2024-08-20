package com.github.b4s1ccoder.progressibility.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.github.b4s1ccoder.progressibility.entity.User;
import com.github.b4s1ccoder.progressibility.repository.UserRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            User userZ = user.get();
            UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                    .username(userZ.getEmail())
                    .password(userZ.getPassword())
                    .roles(userZ.getRoles().toArray(new String[0]))
                    .build();

            return userDetails;
        }

        throw new UsernameNotFoundException("No user associated with id: "+ id);
    }
}
