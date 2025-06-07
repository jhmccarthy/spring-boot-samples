package com.jimmccarthy.rugby.controller;

import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jimmccarthy.rugby.model.dto.PlayerTeamDto;
import com.jimmccarthy.rugby.model.dto.TeamDto;

@RestController
@RequestMapping(path = "/api/v1/rugby/mgr", produces = MediaType.APPLICATION_JSON_VALUE)
public class ManagerController {
    /**
     * Update a team's information.
     *
     * @param id the ID of the team
     * @param teamName the name of the team
     * @param stadium the name of the stadium
     * @return the updated team data
     */
    @PostMapping(path = "/teams/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RestApiResponse<TeamDto> updateTeam(@PathVariable Integer id,
                                               @RequestParam Optional<String> teamName,
                                               @RequestParam Optional<String> stadium) {
        TeamDto payload = null;

        return RestApiResponseBuilder.createBuilder(payload).getResponse();
    }

    /**
     * Update a player's information.
     *
     * @param id the ID of the player
     * @param name the name of the player
     * @param position the position the player plays
     * @param jerseyNumber the jersey number of the player
     * @return the updated player data
     */
    @PostMapping("/players/{id}")
    public RestApiResponse<PlayerTeamDto> updatePlayer(@PathVariable Integer id,
                                                       @RequestParam Optional<String> name,
                                                       @RequestParam Optional<String> position,
                                                       @RequestParam Optional<String> jerseyNumber) {
        PlayerTeamDto payload = null;

        return RestApiResponseBuilder.createBuilder(payload).getResponse();
    }

    /**
     * Suspend a given player.
     *
     * @param id the ID of the player
     * @return the updated player data
     */
    @PutMapping("/players/{id}/suspend")
    public RestApiResponse<PlayerTeamDto> suspendPlayer(@PathVariable Integer id) {
        PlayerTeamDto payload = null;

        return RestApiResponseBuilder.createBuilder(payload).getResponse();
    }
}
