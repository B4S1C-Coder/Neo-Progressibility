package com.github.b4s1ccoder.progressibility.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.b4s1ccoder.progressibility.entity.Tag;
import com.github.b4s1ccoder.progressibility.entity.Task;
import com.github.b4s1ccoder.progressibility.entity.Team;
import com.github.b4s1ccoder.progressibility.entity.User;
import com.github.b4s1ccoder.progressibility.repository.TagRepository;
import com.github.b4s1ccoder.progressibility.repository.TaskRepository;
import com.github.b4s1ccoder.progressibility.repository.TeamRepository;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private UserService userService;

    public boolean userIdBelongsToTeam(Team team, String userId) {
        return team.getUsers().stream().anyMatch(
            userOfTeam -> userOfTeam.getId().equals(userId)
        );
    }

    public boolean taskIdBelongsToTeam(Team team, String taskId) {
        return team.getTasks().stream().anyMatch(
            taskOfTeam -> taskOfTeam.getId().equals(taskId)
        );
    }

    public boolean tagIdBelongsToTeam(Team team, String tagId) {
        return team.getTags().stream().anyMatch(
            tagOfTeam -> tagOfTeam.getId().equals(tagId)
        );
    }

    public boolean userIdIsInvited(Team team, String userId) {
        return team.getInvitedUsers().stream().anyMatch(
            userOfTeam -> userOfTeam.getId().equals(userId)
        );
    }

    public boolean teamInvitationIsInUserId(Team team, User user) {
        return user.getTeamInvitations().stream().anyMatch(
            teamInvitation -> teamInvitation.getId().equals(team.getId())
        );
    }

    public Optional<Team> findById(String id) {
        return teamRepository.findById(id);
    }

    public Team createTeamUserObj(Team team, User owner) {

        String teamUserObjIdentifier = Long.toString(System.currentTimeMillis() / 1000L)
                .concat("@".concat(owner.getId()));

        User teamUserObj = new User(teamUserObjIdentifier, teamUserObjIdentifier);
        User savedTeamUserObj = userService.save(teamUserObj, false);
        team.setTeamUserObj(savedTeamUserObj);

        Team savedTeam = teamRepository.save(team);

        teamUserObj.getTeams().add(savedTeam);

        userService.save(teamUserObj);
        return savedTeam;
    }

    @Transactional
    public Team addUserToTeam(Team team, User user) {
        if (userIdBelongsToTeam(team, user.getId())) {
            return team;
        }

        team.getUsers().add(user);
        Team savedTeam = teamRepository.save(team);

        user.getTeams().add(savedTeam);
        user.getTeamInvitations().removeIf(
            teamInvitation -> teamInvitation.getId().equals(savedTeam.getId())
        );
        userService.save(user);

        return savedTeam;
    }

    @Transactional
    public Team inviteUserToTeam(Team team, User user) {
        if (userIdIsInvited(team, user.getId())) {
            return team;
        }

        team.getInvitedUsers().add(user);
        Team savedTeam = teamRepository.save(team);

        if (!teamInvitationIsInUserId(savedTeam, user)) {
            user.getTeamInvitations().add(savedTeam);
            userService.save(user, false);
        }

        return savedTeam;
    }

    @Transactional
    public Team removeUserFromTeam(Team team, User user) {
        team.getUsers().removeIf(userOfTeam -> userOfTeam.getId().equals(user.getId()));
        user.getTeams().removeIf(userTeam -> userTeam.getId().equals(team.getId()));

        userService.save(user);
        return teamRepository.save(team);
    }

    public Team addTaskToTeam(Team team, Task task) {
        if (taskIdBelongsToTeam(team, task.getId())) {
            return team;
        }

        team.getTasks().add(task);
        Team savedTeam = teamRepository.save(team);

        return savedTeam;
    }

    @Transactional
    public Team removeTaskFromTeam(Team team, Task task) {
        team.getTasks().removeIf(taskOfTeam -> taskOfTeam.getId().equals(task.getId()));
        
        // Redundant as task will never be associated with the teamUserObj
        // taskService.deleteTask(task.getId(), team.getTeamUserObj());
        // Instead better to directly delete it

        taskRepository.deleteById(task.getId());

        return teamRepository.save(team);
    }

    public Team addTagToTeam(Team team, Tag tag) {
        if (tagIdBelongsToTeam(team, tag.getId())) {
            return team;
        }

        team.getTags().add(tag);
        Team savedTeam = teamRepository.save(team);

        return savedTeam;
    }

    @Transactional
    public Team removeTagFromTeam(Team team, Tag tag) {
        team.getTags().removeIf(tagOfTeam -> tagOfTeam.getId().equals(tag.getId()));
        tagRepository.deleteById(tag.getId());
        return teamRepository.save(team);
    }

    // As the application scales, this should be moved to a cron job and the Team
    // should be soft deleted i.e. marked for deletion and the actual deletion can
    // happen at a scheduled time.
    @Transactional
    public void deleteTeam(Team team) {
        // Remove the team from the member users
        for (User memberUser: team.getUsers()) {
            memberUser.getTeams().removeIf(userTeam -> userTeam.getId().equals(team.getId()));
            userService.save(memberUser);
        }

        // Delete the Tags and Tasks
        for (Task teamTask: team.getTasks()) {
            taskRepository.deleteById(teamTask.getId());
        }

        for (Tag teamTag: team.getTags()) {
            for (Task tagTask: teamTag.getTasks()) {
                taskRepository.deleteById(tagTask.getId());
            }
            tagRepository.deleteById(teamTag.getId());
        }

        // Delete the Team's teamUserObj
        userService.veryDangerous_simpleDelete(team.getTeamUserObj().getId());

        // Delete the Team itself
        teamRepository.delete(team);
    }
}
