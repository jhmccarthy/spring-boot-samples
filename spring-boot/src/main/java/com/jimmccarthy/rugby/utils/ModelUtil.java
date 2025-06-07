package com.jimmccarthy.rugby.utils;

import com.jimmccarthy.rugby.model.dto.PlayerDto;
import com.jimmccarthy.rugby.model.dto.PlayerTeamDto;
import com.jimmccarthy.rugby.model.dto.TeamDto;
import com.jimmccarthy.rugby.model.entity.Player;
import com.jimmccarthy.rugby.model.entity.Team;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ModelUtil {
    /**
     * Convert {@code Team} to a {@code TeamDto}.
     *
     * @param entity the entity object
     * @return the DTO object
     */
    public TeamDto toTeamDto(Team entity) {
        var players = entity.getPlayers().stream() //
                .map(this::toPlayerDto) //
                .toList();

        var dto = new TeamDto(entity.getId(), entity.getName(), entity.getStadium());
        dto.getPlayers().addAll(players);

        return dto;
    }

    /**
     * Convert {@code TeamDto} to a {@code Team}.
     *
     * @param dto the DTO object
     * @return entity the entity object
     */
    public Team toTeam(TeamDto dto) {
        var players = dto.getPlayers().stream() //
                .map(this::toPlayer) //
                .collect(Collectors.toList());

        var entity = new Team();
        entity.setName(dto.getName());
        entity.setStadium(dto.getStadium());
        entity.setPlayers(players);

        return entity;
    }

    /**
     * Convert {@code Player} to a {@code PlayerDto}.
     *
     * @param entity the entity object
     * @return the DTO object
     */
    public PlayerDto toPlayerDto(Player entity) {
        var dto = new PlayerDto();
        loadPlayerDto(dto, entity);

        return dto;
    }

    public Player toPlayer(PlayerDto dto) {
        var entity = new Player();
        loadPlayer(entity, dto);

        return entity;
    }

    /**
     * Convert {@code Player} to a {@code PlayerTeamDto}.
     *
     * @param entity the entity object
     * @return the DTO object
     */
    public PlayerTeamDto toPlayerTeamDto(Player entity) {
        var dto = new PlayerTeamDto();
        loadPlayerDto(dto, entity);

        dto.setTeamName(entity.getTeam().getName());

        return dto;
    }

    /**
     * Load the data for the {@code PlayerDto}.
     *
     * @param dto    the DTO object
     * @param entity the entity object
     */
    private void loadPlayerDto(PlayerDto dto, Player entity) {
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPosition(entity.getPosition());
        dto.setJerseyNumber(entity.getJerseyNo());
    }

    /**
     * Load the data for the {@code Player}.
     *
     * @param entity the entity object
     * @param dto    the DTO object
     */
    private void loadPlayer(Player entity, PlayerDto dto) {
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setPosition(dto.getPosition());
        entity.setJerseyNo(dto.getJerseyNumber());
    }
}
