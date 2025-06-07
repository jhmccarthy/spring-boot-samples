package com.jimmccarthy.rugby.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.jimmccarthy.rugby.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jimmccarthy.rugby.model.dto.PlayerTeamDto;
import com.jimmccarthy.rugby.service.PlayerService;

@RestController
@RequestMapping(path = "/api/v1/rugby", produces = MediaType.APPLICATION_JSON_VALUE)
public class PlayerController {
    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    /**
     * Get all the players or the players with a specific jersey number.
     *
     * @param jerseyNo an optional parameter that will limit the players to their jersey number
     * @return a list of players
     */
    @GetMapping("/players")
    public RestApiResponse<List<PlayerTeamDto>> players(@RequestParam Optional<String> jerseyNo) {
        List<PlayerTeamDto> players = new ArrayList<>();

        if (jerseyNo.isPresent()) {
            players.addAll(playerService.getPlayersByJerseyNumber(jerseyNo.get()));
        } else {
            players.addAll(playerService.getPlayers());
        }

        return RestApiResponseBuilder.createBuilder(players).getResponse();
    }

    /**
     * Get a specific player.
     *
     * @param id the id of the player (not the jersey number)
     * @return the player
     */
    @GetMapping("/players/{id}")
    public RestApiResponse<List<PlayerTeamDto>> player(@PathVariable(required = true) String id) {
        List<PlayerTeamDto> players = new ArrayList<>();
        players.add(playerService.getPlayer(id));

        return RestApiResponseBuilder.createBuilder(players).getResponse();
    }
}
