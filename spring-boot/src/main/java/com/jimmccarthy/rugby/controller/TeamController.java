package com.jimmccarthy.rugby.controller;

import com.jimmccarthy.rugby.model.dto.TeamDto;
import com.jimmccarthy.rugby.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/rugby", produces = MediaType.APPLICATION_JSON_VALUE)
public class TeamController {
    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping(value = {"/teams", "/teams/{id}"})
    public RestApiResponse<List<TeamDto>> teams(@PathVariable Optional<String> id) {
        List<TeamDto> teams = new ArrayList<>();

        if (id.isPresent()) {
            teams.add(teamService.getTeam(id.get()));
        } else {
            teams.addAll(teamService.getTeams());
        }

        return RestApiResponseBuilder.createBuilder(teams).getResponse();
    }
}
