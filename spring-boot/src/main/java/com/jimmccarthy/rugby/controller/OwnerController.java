package com.jimmccarthy.rugby.controller;

import com.jimmccarthy.rugby.model.dto.PlayerTeamDto;
import com.jimmccarthy.rugby.model.dto.TeamDto;
import com.jimmccarthy.rugby.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/rugby/owner", produces = MediaType.APPLICATION_JSON_VALUE)
public class OwnerController {
    private final TeamService teamService;

    @Autowired
    public OwnerController(TeamService teamService) {
        this.teamService = teamService;
    }

    /**
     * Add a new team to the league. Only the team can be added. Players must be added individually to the team.
     * <p>
     * curl --insecure --user ownr1:pa$$Word2 --data "@team.json" --header "Content-Type: application/json"
     * https://localhost:8443/api/v1/rugby/owner/teams
     *
     * @param team the team data
     * @return the new team data
     */
    @PostMapping(path = "/teams", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RestApiResponse<TeamDto> addTeam(@RequestBody TeamDto team) {
        var payload = teamService.addTeam(team);

        return RestApiResponseBuilder.createBuilder(payload).getResponse();
    }

    /**
     * Add a new player to a team.
     *
     * @param player the player data
     * @return the new player data
     */
    @PostMapping(path = "/players", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RestApiResponse<PlayerTeamDto> addPlayer(@RequestBody PlayerTeamDto player) {
        PlayerTeamDto payload = null;

        return RestApiResponseBuilder.createBuilder(payload).getResponse();
    }
}
