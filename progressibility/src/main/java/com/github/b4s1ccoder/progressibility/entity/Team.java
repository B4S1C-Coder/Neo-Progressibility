package com.github.b4s1ccoder.progressibility.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "teams")
@Data
@NoArgsConstructor
public class Team {
    @Id
    private String id;

    private String name;

    @DBRef
    private User owner;

    @DBRef
    private List<User> users = new ArrayList<>();
    @DBRef
    private List<Task> tasks = new ArrayList<>();
    @DBRef
    private List<Tag> tags = new ArrayList<>();
}
