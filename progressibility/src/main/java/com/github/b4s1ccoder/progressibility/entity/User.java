package com.github.b4s1ccoder.progressibility.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

// import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Document(collection = "users")
@Data
// @AllArgsConstructor
public class User {
    @Id
    private String id;

    private String firstName;
    private String lastName;

    @Indexed(unique = true)
    @NonNull
    private String email;

    @NonNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private List<String> roles = new ArrayList<>(Arrays.asList("USER"));
}
