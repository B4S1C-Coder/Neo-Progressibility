package com.github.b4s1ccoder.progressibility.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.b4s1ccoder.progressibility.entity.Tag;
import com.github.b4s1ccoder.progressibility.entity.Task;
import com.github.b4s1ccoder.progressibility.entity.User;
import com.github.b4s1ccoder.progressibility.security.SecurityUtils;
import com.github.b4s1ccoder.progressibility.service.TagService;
import com.github.b4s1ccoder.progressibility.service.TaskService;

@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private SecurityUtils securityUtils;

    @Autowired
    private TagService tagService;

    @Autowired
    private TaskService taskService;

    @GetMapping
    public ResponseEntity<?> getAllTagsOfUser() {
        Optional<User> userOpt = securityUtils.getAuthenticatedUser();

        if (!userOpt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(userOpt.get().getTags(), HttpStatus.OK);
    }

    @GetMapping("/{idToGet}")
    public ResponseEntity<?> getTagById(@PathVariable String idToGet) {
        Optional<User> userOpt = securityUtils.getAuthenticatedUser();

        if (!userOpt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (!tagService.tagIdBelongsToUser(idToGet, userOpt.get())) {
            return new ResponseEntity<>("Tag does not belong to the user.", HttpStatus.FORBIDDEN);
        }

        Optional<Tag> tagOpt = tagService.findById(idToGet);

        if (!tagOpt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(tagOpt.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createTag(@RequestBody Tag tag) {
        Optional<User> userOpt = securityUtils.getAuthenticatedUser();

        if (!userOpt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Tag savedTag = tagService.save(tag, userOpt.get());
        return new ResponseEntity<>(savedTag, HttpStatus.CREATED);
    }

    @PutMapping("/add-task")
    public ResponseEntity<?> addTaskToTag(@RequestBody Map<String, String> body) {
        Optional<User> userOpt = securityUtils.getAuthenticatedUser();

        if (!userOpt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if ((body.get("tagId") == null) || (body.get("taskId") == null)) {
            return new ResponseEntity<>("tagId and taskId both must be provided", HttpStatus.BAD_REQUEST);
        }

        if (!tagService.tagIdBelongsToUser(body.get("tagId"), userOpt.get())) {
            return new ResponseEntity<>("Tag does not belong to the user.", HttpStatus.FORBIDDEN);
        }

        if (!taskService.taskIdBelongsToUser(body.get("taskId"), userOpt.get())) {
            return new ResponseEntity<>("Task does not belong to the user.", HttpStatus.FORBIDDEN);
        }

        Optional<Tag> tagOpt = tagService.findById(body.get("tagId"));
        Optional<Task> taskOpt = taskService.findById(body.get("taskId"));

        if (!tagOpt.isPresent()) {
            return new ResponseEntity<>("Tag not found.", HttpStatus.NOT_FOUND);
        }

        if (!taskOpt.isPresent()) {
            return new ResponseEntity<>("Task not found.", HttpStatus.NOT_FOUND);
        }

        Tag updatedTag = tagService.addTaskToTagAndSave(tagOpt.get(), taskOpt.get(), userOpt.get());

        return new ResponseEntity<>(updatedTag, HttpStatus.OK);
    }

    @PutMapping("/remove-task")
    public ResponseEntity<?> removeTaskFromTag(@RequestBody Map<String, String> body) {
        Optional<User> userOpt = securityUtils.getAuthenticatedUser();

        if (!userOpt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if ((body.get("tagId") == null) || (body.get("taskId") == null)) {
            return new ResponseEntity<>("tagId and taskId both must be provided", HttpStatus.BAD_REQUEST);
        }

        if (!tagService.tagIdBelongsToUser(body.get("tagId"), userOpt.get())) {
            return new ResponseEntity<>("Tag does not belong to the user.", HttpStatus.FORBIDDEN);
        }

        if (!taskService.taskIdBelongsToUser(body.get("taskId"), userOpt.get())) {
            return new ResponseEntity<>("Task does not belong to the user.", HttpStatus.FORBIDDEN);
        }

        Optional<Tag> tagOpt = tagService.findById(body.get("tagId"));
        Optional<Task> taskOpt = taskService.findById(body.get("taskId"));

        if (!tagOpt.isPresent()) {
            return new ResponseEntity<>("Tag not found.", HttpStatus.NOT_FOUND);
        }

        if (!taskOpt.isPresent()) {
            return new ResponseEntity<>("Task not found.", HttpStatus.NOT_FOUND);
        }

        Tag updatedTag = tagService.removeTaskFromTagAndSave(tagOpt.get(), taskOpt.get(), userOpt.get());

        return new ResponseEntity<>(updatedTag, HttpStatus.OK);
    }

    @DeleteMapping("/{idToDelete}")
    public ResponseEntity<?> deleteTag(@PathVariable String idToDelete) {
        Optional<User> userOpt = securityUtils.getAuthenticatedUser();

        if (!userOpt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (!tagService.tagIdBelongsToUser(idToDelete, userOpt.get())) {
            return new ResponseEntity<>("Tag does not belong to user.",HttpStatus.FORBIDDEN);
        }

        int deletionStatus = tagService.deleteTag(idToDelete, userOpt.get());

        if (deletionStatus != 0) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
