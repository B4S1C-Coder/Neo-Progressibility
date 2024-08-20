package com.github.b4s1ccoder.progressibility.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.b4s1ccoder.progressibility.entity.User;
import com.github.b4s1ccoder.progressibility.service.UserDetailServiceImpl;
import com.github.b4s1ccoder.progressibility.service.UserService;
import com.github.b4s1ccoder.progressibility.utils.JWTUtils;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailServiceImpl userDetailServiceImpl;

    @Autowired
    private JWTUtils jwtUtils;

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User creationParameters) {
        User createdUser = userService.save(creationParameters, true);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/detail")
    public ResponseEntity<?> getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();

        Optional<User> user = userService.findById(id);

        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        try {

            // authenticationManager.authenticate(
            //     new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            // );

            // UserDetails userDetails = userDetailServiceImpl.loadUserByUsername(user.getEmail());
            User temp = userService.findByEmail(user.getEmail());
            String token = jwtUtils.generateToken(temp.getId());
            return new ResponseEntity<>(token, HttpStatus.OK);

            // if (temp.isPresent()) {
            //     User userEntity = temp.get();

            //     // if (!userEntity.getPassword().equals(userService.passwordEncode(user.getPassword()))) {
            //     //     return new ResponseEntity<>("Invalid password.", HttpStatus.BAD_REQUEST);
            //     // }

            //     String token = jwtUtils.generateToken(userEntity.getId());
            //     return new ResponseEntity<>(token, HttpStatus.OK);
            // }

            // return new ResponseEntity<>("Invalid credentials", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Error while login: ", e);
            return new ResponseEntity<>("Invalid credentials", HttpStatus.BAD_REQUEST);
        }
    }
}
