package com.github.b4s1ccoder.progressibility.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.b4s1ccoder.progressibility.entity.User;
import com.github.b4s1ccoder.progressibility.security.UserEntityIncludedAuthToken;
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
    private JWTUtils jwtUtils;

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User creationParameters) {
        User createdUser = userService.save(creationParameters, true);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/detail")
    public ResponseEntity<?> getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof UserEntityIncludedAuthToken) {
            UserEntityIncludedAuthToken userEntityIncludedAuthToken = (UserEntityIncludedAuthToken) authentication;
            return new ResponseEntity<>(userEntityIncludedAuthToken.getUser(), HttpStatus.OK);
        }

        return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        try {

            log.info(user.getEmail());
            log.info(user.getPassword());

            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );

            Optional<User> temp = userService.findByEmail(user.getEmail());

            if (temp.isPresent()) {
                User userEntity = temp.get();
                String token = jwtUtils.generateToken(userEntity.getEmail());
                return new ResponseEntity<>(token, HttpStatus.OK);
            }

            return new ResponseEntity<>("Invalid credentials", HttpStatus.BAD_REQUEST);
        } catch (AuthenticationException ae) {
            log.error("Authentication Error: ", ae.getMessage());
            return new ResponseEntity<>("Invalid credentials. Auth error.", HttpStatus.BAD_REQUEST);
        } 
        
        catch (Exception e) {
            log.error("Error while login: ", e);
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
