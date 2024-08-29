package com.github.b4s1ccoder.progressibility.controller;

import java.util.Optional;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.b4s1ccoder.progressibility.entity.Team;
import com.github.b4s1ccoder.progressibility.entity.User;
import com.github.b4s1ccoder.progressibility.security.SecurityUtils;
import com.github.b4s1ccoder.progressibility.service.TeamService;

@RestController
@RequestMapping("/team")
public class TeamController {

    @Autowired
    private SecurityUtils securityUtils;

    @Autowired
    private TeamService teamService;

    @GetMapping
    private ResponseEntity<?> getAllTeamsOfUser() {
        Optional<User> userOpt = securityUtils.getAuthenticatedUser();

        if (!userOpt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        List<Map<String, Object>> userTeams = new ArrayList<>();

        for (Team team: userOpt.get().getTeams()) {
            userTeams.add(team.externalRepr());
        }

        return new ResponseEntity<>(userTeams, HttpStatus.OK);
    }

    @GetMapping("/{teamId}")
    private ResponseEntity<?> getTeam(@PathVariable String teamId) {
        Optional<User> userOpt = securityUtils.getAuthenticatedUser();

        if (!userOpt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (!userOpt.get().getTeams().stream().anyMatch(
            userTeam -> userTeam.getId().equals(teamId))) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Optional<Team> teamOpt = teamService.findById(teamId);

        if (!teamOpt.isPresent()) {
            // Possible database inconsistency
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(teamOpt.get().externalRepr(), HttpStatus.OK);
    }

    @PostMapping
    private ResponseEntity<?> createTeam(@RequestBody Team team) {
        Optional<User> userOpt = securityUtils.getAuthenticatedUser();

        if (!userOpt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User owner = userOpt.get();

        team.setOwner(owner);
        Team updatedTeam = teamService.addUserToTeam(team, owner);
        Team savedTeam = teamService.createTeamUserObj(updatedTeam, owner);

        return new ResponseEntity<>(savedTeam.externalRepr(), HttpStatus.OK);
    }
}
