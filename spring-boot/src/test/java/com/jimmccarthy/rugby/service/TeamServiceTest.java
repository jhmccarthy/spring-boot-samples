package com.jimmccarthy.rugby.service;

import com.jimmccarthy.rugby.model.dto.PlayerDto;
import com.jimmccarthy.rugby.model.entity.Player;
import com.jimmccarthy.rugby.model.entity.Team;
import com.jimmccarthy.rugby.repository.TeamRepository;
import com.jimmccarthy.rugby.utils.ModelUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TeamServiceTest {
    private static final Long TEAM_ID = (long) 42;
    private static final Long PLAYER_ID = (long) 45;
    private Team team = null;
    private List<Team> teams = null;

    @Mock
    private TeamRepository teamRepository;

    @Spy
    private ModelUtil modelUtil;

    @Captor
    private ArgumentCaptor<Team> captor;

    @InjectMocks
    private TeamService service;

    @BeforeEach
    public void init() {
        team = new Team();
        team.setId(TEAM_ID);
        team.setName("All Blacks");
        team.setStadium("Eden Park");

        var player = new Player();
        player.setId(PLAYER_ID);
        player.setName("Jonah Lomu");
        player.setPosition("Winger");
        player.setJerseyNo(11);
        player.setTeam(team);

        var players = new ArrayList<Player>();
        players.add(player);

        team.setPlayers(players);

        teams = new ArrayList<Team>();
        teams.add(team);
    }

    @Test
    public void getTeams() {
        when(teamRepository.findAll()).thenReturn(teams);

        var result = service.getTeams();
        verify(teamRepository).findAll();

        assertThat(result, is(notNullValue()));
        assertThat(result, hasSize(1));

        var team = result.getFirst();
        assertThat(team.getId(), is(TEAM_ID));
        assertThat(team.getName(), is("All Blacks"));
        assertThat(team.getStadium(), is("Eden Park"));

        assertThat(team.getPlayers(), is(notNullValue()));
        assertThat(team.getPlayers(), hasSize(1));

        PlayerDto player = team.getPlayers().getFirst();
        assertThat(player.getId(), is(PLAYER_ID));
        assertThat(player.getName(), is("Jonah Lomu"));
        assertThat(player.getPosition(), is("Winger"));
        assertThat(player.getJerseyNumber(), is(11));
    }

    @Test
    public void getTeam() {
        when(teamRepository.findById(anyLong())).thenReturn(Optional.of(team));

        var team = service.getTeam(TEAM_ID.toString());
        verify(teamRepository).findById(anyLong());

        assertThat(team, is(notNullValue()));
        assertThat(team.getId(), is(TEAM_ID));
        assertThat(team.getName(), is("All Blacks"));
        assertThat(team.getStadium(), is("Eden Park"));

        assertThat(team.getPlayers(), is(notNullValue()));
        assertThat(team.getPlayers(), hasSize(1));

        var player = team.getPlayers().getFirst();
        assertThat(player.getId(), is(PLAYER_ID));
        assertThat(player.getName(), is("Jonah Lomu"));
        assertThat(player.getPosition(), is("Winger"));
        assertThat(player.getJerseyNumber(), is(11));
    }

    @Test
    public void teamsNotFound() {
        when(teamRepository.findAll()).thenReturn(new ArrayList<>());

        var result = service.getTeams();
        verify(teamRepository).findAll();

        assertThat(result, is(notNullValue()));
        assertThat(result, is(empty()));
    }

    @Test
    public void teamNotFound() {
        when(teamRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> service.getTeam("23"));
    }

    @Test
    public void idNotValid() {
        var e = assertThrows(AppServiceException.class, () -> service.getTeam("AB"));

        verify(teamRepository, never()).findById(anyLong());
        assertThat(e.getMessage(), is("The id AB must be numeric"));
    }
}
