package com.github.b4s1ccoder.progressibility.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Document(collection = "tags")
@Data
public class Tag {
    @Id
    private String id;

    private String name;

    @DBRef
    @JsonIgnore
    private User user;

    @DBRef
    private List<Task> tasks = new ArrayList<>();
}
