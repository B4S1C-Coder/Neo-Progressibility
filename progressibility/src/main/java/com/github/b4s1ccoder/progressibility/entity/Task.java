package com.github.b4s1ccoder.progressibility.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Document(collection = "tasks")
@Data
public class Task {
    @Id
    private String id;

    private String content;
    private LocalDateTime dateAdded = LocalDateTime.now();
    private LocalDateTime dateDue;
    private boolean completed = false;

    @DBRef
    @JsonIgnore
    private User user;
}
