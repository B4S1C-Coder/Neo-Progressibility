package com.github.b4s1ccoder.progressibility.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusCheck {

    @GetMapping("/status")
    public ResponseEntity<String> backendUpOrNot() {
        return new ResponseEntity<>("Backend is up and running.", HttpStatus.OK);
    }
}
