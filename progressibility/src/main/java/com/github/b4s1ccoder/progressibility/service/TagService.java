package com.github.b4s1ccoder.progressibility.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.b4s1ccoder.progressibility.entity.Tag;
import com.github.b4s1ccoder.progressibility.entity.Task;
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

    public boolean taskIdBelongsToTag(String taskId, Tag tag) {
        return tag.getTasks().stream().anyMatch(
            taskOfTag -> taskOfTag.getId().equals(taskId)
        );
    }

    public Optional<Tag> findById(String id) {
        return tagRepository.findById(id);
    }

    @Transactional
    public Tag save(Tag tag, User user) {
        if ((tag.getId() != null) && (tagIdBelongsToUser(tag.getId(), user))) {

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

    @Transactional
    public Tag addTaskToTagAndSave(Tag tag, Task task, User user) {

        if ((tag.getId() == null)||(!tagIdBelongsToUser(tag.getId(), user))) {
            // Silently fail, as if tag id is null, or tag doesn't belong to user,
            // then on calling save() would result in the current tag's id being discarded
            // and a new id be assigned to it which can cause database inconsistencies, and
            // could also serve as a potential unauthorized delete action.
            return tag;
        }

        if (task.getId() != null) {

            if (taskIdBelongsToTag(task.getId(), tag)) {
                return tag;
            }

            tag.getTasks().add(task);
            return save(tag, user);
        } else {

            Task updatedTask = taskService.save(task, user);

            tag.getTasks().add(updatedTask);
            Tag savedTag = save(tag, user);
            
            return savedTag;
        }
    }

    @Transactional
    public Tag removeTaskFromTagAndSave(Tag tag, Task task, User user) {
        if ((tag.getId() == null) || (!tagIdBelongsToUser(tag.getId(), user))) {
            // Silently fail, as if tag id is null, or tag doesn't belong to user,
            // then on calling save() would result in the current tag's id being discarded
            // and a new id be assigned to it which can cause database inconsistencies, and
            // could also serve as a potential unauthorized delete action.
            return tag;
        }

        if ((task.getId() == null) || (!taskIdBelongsToTag(task.getId(), tag))) {
            return tag;
        }

        tag.getTasks().removeIf(taskOfTag -> taskOfTag.getId().equals(task.getId()));
        return save(tag, user);
    }

    @Transactional
    public int deleteTag(String tagId, User user) {
        if (!tagIdBelongsToUser(tagId, user)) {
            return -1;
        }

        user.getTags().removeIf(usertag -> usertag.getId().equals(tagId));
        userService.save(user);

        tagRepository.deleteById(tagId);
        return 0;
    }

}
