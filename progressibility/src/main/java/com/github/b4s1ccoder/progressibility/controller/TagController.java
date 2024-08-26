package com.github.b4s1ccoder.progressibility.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.b4s1ccoder.progressibility.entity.User;
import com.github.b4s1ccoder.progressibility.security.SecurityUtils;
import com.github.b4s1ccoder.progressibility.service.TagService;

@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private SecurityUtils securityUtils;

    @Autowired
    private TagService tagService;

    @GetMapping
    public ResponseEntity<?> getAllTagsOfUser() {
        Optional<User> userOpt = securityUtils.getAuthenticatedUser();

        if (!userOpt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(userOpt.get().getTags(), HttpStatus.OK);
    }

    
}
