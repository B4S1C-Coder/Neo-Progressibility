package com.github.b4s1ccoder.progressibility.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.b4s1ccoder.progressibility.entity.Task;
import com.github.b4s1ccoder.progressibility.entity.User;
import com.github.b4s1ccoder.progressibility.security.SecurityUtils;
import com.github.b4s1ccoder.progressibility.service.TaskService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private SecurityUtils securityUtils;

    @Autowired
    private TaskService taskService;

    @GetMapping
    public ResponseEntity<?> getAllTasksOfUser() {
        Optional<User> userOpt = securityUtils.getAuthenticatedUser();

        if (!userOpt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(userOpt.get().getTasks(), HttpStatus.OK);
    }

    @GetMapping("/{idToGet}")
    public ResponseEntity<?> getTaskById(@PathVariable String idToGet) {
        Optional<User> userOpt = securityUtils.getAuthenticatedUser();

        if (!userOpt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user = userOpt.get();

        if (!taskService.taskIdBelongsToUser(idToGet, user)) {
            return new ResponseEntity<>("Task does not belong to user.",
                        HttpStatus.FORBIDDEN);
        }

        Optional<Task> taskOpt = taskService.findById(idToGet);

        if (!taskOpt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(taskOpt.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody Task task) {
        Optional<User> userOpt = securityUtils.getAuthenticatedUser();

        if (!userOpt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        log.info("Starting save process");
        log.info("userOpt email: "+userOpt.get().getEmail());

        Task savedTask = taskService.save(task, userOpt.get());
        return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
    }

    @PutMapping("/{idToUpdate}")
    @PatchMapping("/{idToUpdate}")
    public ResponseEntity<?> updateTask(@PathVariable String idToUpdate, @RequestBody Task task) {
        Optional<User> userOpt = securityUtils.getAuthenticatedUser();

        if (!userOpt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user = userOpt.get();

        if (!taskService.taskIdBelongsToUser(idToUpdate, user)) {
            return new ResponseEntity<>("Task does not belong to user.",
                        HttpStatus.FORBIDDEN);
        }

        Optional<Task> oldTaskOpt = taskService.findById(idToUpdate);
        
        if (!oldTaskOpt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Task updatedTask = taskService.update(oldTaskOpt.get(), task, user);

        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    @DeleteMapping("/{idToDelete}")
    public ResponseEntity<?> deleteTask(@PathVariable String idToDelete) {
        Optional<User> userOpt = securityUtils.getAuthenticatedUser();

        if (!userOpt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        int deletionStatus = taskService.deleteTask(idToDelete, userOpt.get());

        if (deletionStatus != 0) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
