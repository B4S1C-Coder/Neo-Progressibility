package com.github.b4s1ccoder.progressibility.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.b4s1ccoder.progressibility.entity.Tag;
import com.github.b4s1ccoder.progressibility.entity.User;
import com.github.b4s1ccoder.progressibility.repository.TagRepository;

@Service
public class TagService {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private TagRepository tagRepository;

    public boolean tagIdBelongsToUser(String tagId, User user) {
        return user.getTags().stream().anyMatch(
            usertag -> usertag.getId().equals(tagId)
        );
    }

    public Optional<Tag> findById(String id) {
        return tagRepository.findById(id);
    }

    public Tag save(Tag tag, User user) {
        if ((tag.getId() != null)&&(tagIdBelongsToUser(tag.getId(), user))) {
            tag.setUser(user);
            return tagRepository.save(tag);
        } else {
            tag.setId(null);
            tag.setUser(user);

            Tag savedTag = tagRepository.save(tag);

            user.getTags().add(savedTag);
            userService.save(user);
            return savedTag;
        }
    }

}
