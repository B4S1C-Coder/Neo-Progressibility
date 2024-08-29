package com.github.b4s1ccoder.progressibility.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Document(collection = "users")
@Data
@NoArgsConstructor
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

    @DBRef
    private List<Task> tasks = new ArrayList<>();

    @DBRef
    private List<Tag> tags = new ArrayList<>();

    @DBRef
    private List<Team> teams = new ArrayList<>();

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // This will return a representation that removes any sensitive user specific data
    // that should not be visible to others. For example we don't want other members of
    // the team to be able to see the personal tasks and tags of current user. This stripped
    // representation would be sent to them to only show the bare minimum required.
    public Map<String, String> externalRepr() {
        
        Map<String, String> repr = new HashMap<String, String>();
        repr.put("email", this.email);
        repr.put("firstName", this.firstName);
        repr.put("lastName", this.lastName);

        return repr;
    }
}
