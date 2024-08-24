package com.github.b4s1ccoder.progressibility.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.b4s1ccoder.progressibility.entity.Task;
import com.github.b4s1ccoder.progressibility.entity.User;
import com.github.b4s1ccoder.progressibility.repository.TaskRepository;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    public Optional<Task> findById(String id) {
        return taskRepository.findById(id);
    }

    public Task save(Task task, User user) {
        boolean taskAlreadyExistsInUser = user.getTasks().stream().anyMatch(
            usertask -> usertask.getId().equals(task.getId())
        );

        if (!taskAlreadyExistsInUser) {
            user.getTasks().add(task);
            userService.save(user);
        }

        task.setUser(user);
        return taskRepository.save(task);
    }
}
