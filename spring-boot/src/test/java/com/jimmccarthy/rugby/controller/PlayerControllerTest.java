package com.jimmccarthy.rugby.controller;

import com.jimmccarthy.rugby.model.dto.PlayerTeamDto;
import com.jimmccarthy.rugby.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PlayerController.class)
@AutoConfigureMockMvc
public class PlayerControllerTest {
    private static final String PLAYERS_URL = "/api/v1/rugby/players";
    private static final Long PLAYER_ID = (long) 45;
    private final MockMvc mvc;
    private final PlayerService playerService;

    @Autowired
    public PlayerControllerTest(MockMvc mvc, PlayerService playerService) {
        this.mvc = mvc;
        this.playerService = playerService;
    }

    @BeforeEach
    public void init() {
        var player = new PlayerTeamDto();
        player.setId(PLAYER_ID);
        player.setName("Jonah Lomu");
        player.setPosition("Winger");
        player.setJerseyNumber(11);
        player.setTeamName("All Blacks");

        var players = new ArrayList<PlayerTeamDto>();
        players.add(player);

        when(playerService.getPlayers()).thenReturn(players);
        when(playerService.getPlayer(anyString())).thenReturn(player);
        when(playerService.getPlayersByJerseyNumber(anyString())).thenReturn(players);
    }

    @Test
    public void players() throws Exception {
        mvc.perform(get(PLAYERS_URL)) //
                .andExpect(status().isOk()) //
                .andExpect(content().contentType("application/json")) //
                .andExpect(jsonPath("$.payload").isArray()) //
                .andExpect(jsonPath("$.payload[0].id", is(45))) //
                .andExpect(jsonPath("$.payload[0].name", is("Jonah Lomu"))) //
                .andExpect(jsonPath("$.payload[0].position", is("Winger"))) //
                .andExpect(jsonPath("$.payload[0].jerseyNumber", is(11))) //
                .andExpect(jsonPath("$.payload[0].teamName", is("All Blacks")));
    }

    @Test
    public void player() throws Exception {
        mvc.perform(get(PLAYERS_URL + "/{id}", PLAYER_ID)) //
                .andExpect(status().isOk()) //
                .andExpect(content().contentType("application/json")) //
                .andExpect(jsonPath("$.payload").isArray()) //
                .andExpect(jsonPath("$.payload[0].id", is(45))) //
                .andExpect(jsonPath("$.payload[0].name", is("Jonah Lomu"))) //
                .andExpect(jsonPath("$.payload[0].position", is("Winger"))) //
                .andExpect(jsonPath("$.payload[0].jerseyNumber", is(11))) //
                .andExpect(jsonPath("$.payload[0].teamName", is("All Blacks")));
    }

    @Test
    public void playersByJerseyNumber() throws Exception {
        mvc.perform(get(PLAYERS_URL).param("jerseyNo", "11")) //
                .andExpect(status().isOk()) //
                .andExpect(content().contentType("application/json")) //
                .andExpect(jsonPath("$.payload").isArray()) //
                .andExpect(jsonPath("$.payload[0].id", is(45))) //
                .andExpect(jsonPath("$.payload[0].name", is("Jonah Lomu"))) //
                .andExpect(jsonPath("$.payload[0].position", is("Winger"))) //
                .andExpect(jsonPath("$.payload[0].jerseyNumber", is(11))) //
                .andExpect(jsonPath("$.payload[0].teamName", is("All Blacks")));
    }
}
