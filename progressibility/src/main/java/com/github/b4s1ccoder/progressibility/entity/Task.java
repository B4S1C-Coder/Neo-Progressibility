package com.github.b4s1ccoder.progressibility.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "tasks")
@Data
public class Task {
    @Id
    private String id;

    private String content;
    private LocalDateTime dateAdded;
    private LocalDateTime dateDue;

    @DBRef
    private User user;
}
