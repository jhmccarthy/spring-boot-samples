package com.jimmccarthy.rugby.controller;

import com.jimmccarthy.rugby.model.dto.TeamDto;
import com.jimmccarthy.rugby.service.TeamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TeamController.class)
public class TeamControllerTest {
    private static final String TEAMS_URL = "/api/v1/rugby/teams";
    private static final Long TEAM_ID = (long) 42;
    private final MockMvc mockMvc;
    private final TeamService teamService;

    @Autowired
    public TeamControllerTest(MockMvc mockMvc, TeamService teamService) {
        this.mockMvc = mockMvc;
        this.teamService = teamService;
    }

    @BeforeEach
    public void init() {
        var team = new TeamDto();
        team.setId(TEAM_ID);
        team.setName("All Blacks");
        team.setStadium("Eden Park");

        var teams = new ArrayList<TeamDto>();
        teams.add(team);

        when(teamService.getTeams()).thenReturn(teams);
        when(teamService.getTeam(anyString())).thenReturn(team);
    }

    @Test
    public void teams() throws Exception {
        mockMvc.perform(get(TEAMS_URL)) //
                .andExpect(status().isOk()) //
                .andExpect(content().contentType("application/json")) //
                .andExpect(jsonPath("$.payload").isArray()) //
                .andExpect(jsonPath("$.payload[0].id", is(42))) //
                .andExpect(jsonPath("$.payload[0].name", is("All Blacks"))) //
                .andExpect(jsonPath("$.payload[0].stadium", is("Eden Park")));
    }

    @Test
    public void team() throws Exception {
        mockMvc.perform(get(TEAMS_URL + "/{id}", TEAM_ID)) //
                .andExpect(status().isOk()) //
                .andExpect(content().contentType("application/json")) //
                .andExpect(jsonPath("$.payload").isArray()) //
                .andExpect(jsonPath("$.payload[0].id", is(42))) //
                .andExpect(jsonPath("$.payload[0].name", is("All Blacks"))) //
                .andExpect(jsonPath("$.payload[0].stadium", is("Eden Park")));
    }
}
