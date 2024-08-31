package com.github.b4s1ccoder.progressibility.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    @JsonIgnore
    private User teamUserObj;

    @DBRef
    private User owner;

    @DBRef
    @JsonIgnore
    private List<User> users = new ArrayList<>();
    @DBRef
    @JsonIgnore
    private List<User> invitedUsers = new ArrayList<>();
    @DBRef
    @JsonIgnore
    private List<Task> tasks = new ArrayList<>();
    @DBRef
    @JsonIgnore
    private List<Tag> tags = new ArrayList<>();

    // This method provides a safe representation for the team object.
    public Map<String, Object> externalRepr() {

        Map<String, Object> repr = new HashMap<String, Object>();

        repr.put("id", this.id);
        repr.put("name", this.name);
        repr.put("owner", this.owner.externalRepr());
        repr.put("tasks", this.tasks);
        repr.put("tags", this.tags);

        List<Map<String, String>> memberReprs = new ArrayList<>();

        for (User memberUser: this.users) {
            memberReprs.add(memberUser.externalRepr());
        }

        repr.put("users", memberReprs);

        return repr;
    }
}
