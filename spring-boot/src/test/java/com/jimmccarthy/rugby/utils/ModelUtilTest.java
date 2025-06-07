package com.jimmccarthy.rugby.utils;

import com.jimmccarthy.rugby.model.entity.Player;
import com.jimmccarthy.rugby.model.entity.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class ModelUtilTest {
    private static final Long TEAM_ID = (long) 42;
    private static final Long PLAYER_ID = (long) 45;
    private Team team = null;
    private Player player = null;

    @Spy
    private ModelUtil util;

    @BeforeEach
    public void init() {
        team = new Team();
        team.setId(TEAM_ID);
        team.setName("All Blacks");
        team.setStadium("Eden Park");

        player = new Player();
        player.setId(PLAYER_ID);
        player.setName("Jonah Lomu");
        player.setPosition("Winger");
        player.setJerseyNo(11);
        player.setTeam(team);

        team.setPlayers(new ArrayList<>());
        team.getPlayers().add(player);
    }

    @Test
    public void convertToTeamDto() {
        var teamDto = util.toTeamDto(team);

        assertNotNull(teamDto);
        assertEquals(TEAM_ID, teamDto.getId());
        assertEquals("All Blacks", teamDto.getName());
        assertEquals("Eden Park", teamDto.getStadium());

        assertNotNull(teamDto.getPlayers());
        assertEquals(1, teamDto.getPlayers().size());

        var playerDto = teamDto.getPlayers().getFirst();
        assertEquals(PLAYER_ID, playerDto.getId());
        assertEquals("Jonah Lomu", playerDto.getName());
        assertEquals("Winger", playerDto.getPosition());
        assertEquals(11, playerDto.getJerseyNumber());
    }

    @Test
    public void convertToPlayerDto() {
        var playerDto = util.toPlayerDto(player);

        assertEquals(PLAYER_ID, playerDto.getId());
        assertEquals("Jonah Lomu", playerDto.getName());
        assertEquals("Winger", playerDto.getPosition());
        assertEquals(11, playerDto.getJerseyNumber());
    }

    @Test
    public void convertToPlayerTeamDto() {
        var playerDto = util.toPlayerTeamDto(player);

        assertEquals(PLAYER_ID, playerDto.getId());
        assertEquals("Jonah Lomu", playerDto.getName());
        assertEquals("Winger", playerDto.getPosition());
        assertEquals(11, playerDto.getJerseyNumber());
        assertEquals("All Blacks", playerDto.getTeamName());
    }
}
