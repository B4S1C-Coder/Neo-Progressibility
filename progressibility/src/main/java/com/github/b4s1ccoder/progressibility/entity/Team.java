package com.github.b4s1ccoder.progressibility.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "teams")
@Data
public class Team {
    @Id
    private String id;

    private List<User> users = new ArrayList<>();
    private List<Task> tasks = new ArrayList<>();
    private List<Tag> tags = new ArrayList<>();
}
