package com.jimmccarthy.rugby.service;

import com.jimmccarthy.rugby.model.entity.Player;
import com.jimmccarthy.rugby.model.entity.Team;
import com.jimmccarthy.rugby.repository.PlayerRepository;
import com.jimmccarthy.rugby.utils.ModelUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceTest {
    private static final Long TEAM_ID = (long) 42;
    private static final Long PLAYER_ID = (long) 45;
    private List<Player> players;
    private Player player;

    @Mock
    private PlayerRepository playerRepository;

    @Spy
    private ModelUtil modelUtil;

    @InjectMocks
    private PlayerService service;

    @BeforeEach
    public void init() throws Exception {
        var team = new Team();
        team.setId(TEAM_ID);
        team.setName("All Blacks");

        player = new Player();
        player.setId(PLAYER_ID);
        player.setName("Jonah Lomu");
        player.setPosition("Winger");
        player.setJerseyNo(11);
        player.setTeam(team);

        players = new ArrayList<Player>();
        players.add(player);

        team.setPlayers(players);
    }

    @Test
    public void getPlayers() {
        when(playerRepository.findAll()).thenReturn(players);

        var result = service.getPlayers();
        verify(playerRepository).findAll();

        assertThat(result, is(notNullValue()));
        assertThat(result, hasSize(1));

        var player = result.getFirst();
        assertThat(player.getId(), is(PLAYER_ID));
        assertThat(player.getName(), is("Jonah Lomu"));
        assertThat(player.getPosition(), is("Winger"));
        assertThat(player.getJerseyNumber(), is(11));
        assertThat(player.getTeamName(), is("All Blacks"));
    }

    @Test
    public void getPlayersByJerseyNumber() {
        when(playerRepository.findAllByJerseyNo(anyInt())).thenReturn(players);

        var result = service.getPlayersByJerseyNumber("11");
        verify(playerRepository).findAllByJerseyNo(anyInt());

        assertThat(result, is(notNullValue()));
        assertThat(result, hasSize(1));

        var player = result.getFirst();
        assertThat(player.getId(), is(PLAYER_ID));
        assertThat(player.getName(), is("Jonah Lomu"));
        assertThat(player.getPosition(), is("Winger"));
        assertThat(player.getJerseyNumber(), is(11));
        assertThat(player.getTeamName(), is("All Blacks"));
    }

    @Test
    public void getPlayer() {
        when(playerRepository.findById(anyLong())).thenReturn(Optional.of(player));

        var result = service.getPlayer(PLAYER_ID.toString());
        verify(playerRepository).findById(anyLong());

        assertThat(result, is(notNullValue()));
        assertThat(result.getId(), is(PLAYER_ID));
        assertThat(result.getName(), is("Jonah Lomu"));
        assertThat(result.getPosition(), is("Winger"));
        assertThat(result.getJerseyNumber(), is(11));
        assertThat(result.getTeamName(), is("All Blacks"));
    }

    @Test
    public void playersNotFound() {
        when(playerRepository.findAll()).thenReturn(new ArrayList<>());

        var result = service.getPlayers();
        verify(playerRepository).findAll();

        assertThat(result, is(notNullValue()));
        assertThat(result, is(empty()));
    }

    @Test
    public void playerNotFound() {
        when(playerRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> service.getPlayer("23"));
    }

    @Test
    public void playerJerseyNotFound() {
        when(playerRepository.findAllByJerseyNo(anyInt())).thenReturn(new ArrayList<>());

        var result = service.getPlayersByJerseyNumber("11");
        verify(playerRepository).findAllByJerseyNo(anyInt());

        assertThat(result, is(notNullValue()));
        assertThat(result, is(empty()));
    }

    @Test
    public void idNotValid() {
        var e = assertThrows(AppServiceException.class, () -> service.getPlayer("AB"));

        verify(playerRepository, never()).findById(anyLong());
        assertThat(e.getMessage(), is("The id AB must be numeric"));
    }
}
