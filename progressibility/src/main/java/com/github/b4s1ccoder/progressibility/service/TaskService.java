package com.github.b4s1ccoder.progressibility.service;

import java.util.Optional;
import java.time.LocalDateTime;

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

    public boolean taskIdBelongsToUser(String taskId, User user) {
        return user.getTasks().stream().anyMatch(
            usertask -> usertask.getId().equals(taskId)
        );
    }

    public Optional<Task> findById(String id) {
        return taskRepository.findById(id);
    }

    public Task save(Task task, User user) {
        if ((task.getId() != null)&&(taskIdBelongsToUser(task.getId(), user))) {
            task.setUser(user);
            return taskRepository.save(task);
        } else {
            // We basically ignore the id and create a new task.
            task.setId(null);
            task.setUser(user);
            Task savedTask = taskRepository.save(task);

            user.getTasks().add(savedTask);
            userService.save(user);

            return savedTask;
        }
    }

    // This function does NOT employ extra checks and is meant to be used only
    // after all the neccessary checks are performed BEFORE calling this function.
    public Task update(Task oldTask, Task task, User user) {
        oldTask.setContent(
            (task.getContent() != null && !task.getContent().equals(""))
            ? task.getContent() : oldTask.getContent()
        );
        
        oldTask.setCompleted(
            (task.isCompleted() != oldTask.isCompleted())
            ? task.isCompleted() : oldTask.isCompleted()
        );

        if (task.getDateDue() != null) {
            oldTask.setDateDue(
                (task.getDateDue().isEqual(LocalDateTime.now()) || task.getDateDue().isAfter(LocalDateTime.now()))
                ? task.getDateDue() : oldTask.getDateDue()
            );
        }

        return save(oldTask, user);
    }

    public int deleteTask(String taskId, User user) {

        // The task id does not exist in the user, so it likely means that this is
        // either a database inconsistency OR that this task id does not belong to
        // the provided user. In both these cases we will just fail silently, since
        // this won't really affect anything from the user's POV and is likely an
        // internal server issue or someone is trying to conduct an unauthorized
        // action.

        if (!taskIdBelongsToUser(taskId, user)) {
            return -1; // This is to indicate a potential unauthorized delete attempt
        }

        user.getTasks().removeIf(usertask -> usertask.getId().equals(taskId));
        userService.save(user);

        taskRepository.deleteById(taskId);
        return 0;
    }
}
