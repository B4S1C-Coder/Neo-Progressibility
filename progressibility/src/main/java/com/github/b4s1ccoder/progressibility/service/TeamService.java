package com.github.b4s1ccoder.progressibility.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.b4s1ccoder.progressibility.entity.Team;
import com.github.b4s1ccoder.progressibility.entity.User;
import com.github.b4s1ccoder.progressibility.repository.TeamRepository;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserService userService;

    public boolean userIdBelongsToTeam(Team team, String userId) {
        return team.getUsers().stream().anyMatch(
            userOfTeam -> userOfTeam.getId().equals(userId)
        );
    }

    public Optional<Team> findById(String id) {
        return teamRepository.findById(id);
    }

    @Transactional
    public Team addUserToTeam(Team team, User user) {
        if (userIdBelongsToTeam(team, user.getId())) {
            return team;
        }

        team.getUsers().add(user);
        Team savedTeam = teamRepository.save(team);

        user.getTeams().add(savedTeam);
        userService.save(user);

        return savedTeam;
    }

    @Transactional
    public Team removeUserFromTeam(Team team, User user) {
        team.getUsers().removeIf(userOfTeam -> userOfTeam.getId().equals(user.getId()));
        user.getTeams().removeIf(userTeam -> userTeam.getId().equals(team.getId()));

        userService.save(user);
        return teamRepository.save(team);
    }
}
