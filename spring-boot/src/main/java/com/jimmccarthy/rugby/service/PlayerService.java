package com.jimmccarthy.rugby.service;

import com.jimmccarthy.rugby.model.dto.PlayerTeamDto;
import com.jimmccarthy.rugby.repository.PlayerRepository;
import com.jimmccarthy.rugby.utils.ModelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@Slf4j
public class PlayerService {
    private static final String GET_PLAYERS_COUNT_MSG = "Found {} players";
    private final PlayerRepository playerRepository;
    private final ModelUtil modelUtil;

    @Autowired
    public PlayerService(PlayerRepository playerRepository, ModelUtil modelUtil) {
        this.playerRepository = playerRepository;
        this.modelUtil = modelUtil;
    }

    /**
     * Get a list of all the players.
     *
     * @return the list of players
     */
    public List<PlayerTeamDto> getPlayers() {
        var players = playerRepository.findAll();
        log.info(GET_PLAYERS_COUNT_MSG, players.size());

        return players.stream() //
                .map(modelUtil::toPlayerTeamDto) //
                .collect(Collectors.toList());
    }

    /**
     * Find a list of players by their jersey number.
     *
     * @param jerseyNo the jersey number
     * @return the list of players
     * @throws AppServiceException if the {@code jerseyNo} is not numeric
     */
    public List<PlayerTeamDto> getPlayersByJerseyNumber(String jerseyNo) {
        if (!StringUtils.isNumeric(jerseyNo)) {
            throw new AppServiceException(String.format("The jersey number %s must be numeric", jerseyNo));
        }

        var players = playerRepository.findAllByJerseyNo(Integer.parseInt(jerseyNo));
        log.info(GET_PLAYERS_COUNT_MSG, players.size());

        return players.stream() //
                .map(modelUtil::toPlayerTeamDto) //
                .collect(Collectors.toList());
    }

    /**
     * Find a specific player.
     *
     * @param id the id of the player
     * @return the player
     * @throws AppServiceException    if the {@code id} is not numeric
     * @throws NoSuchElementException if the player could not be found
     */
    public PlayerTeamDto getPlayer(String id) {
        if (!StringUtils.isNumeric(id)) {
            throw new AppServiceException(String.format("The id %s must be numeric", id));
        }

        var player = playerRepository.findById(Long.parseLong(id)).orElseThrow();
        log.info("Found player {}", player.getName());

        return modelUtil.toPlayerTeamDto(player);
    }
}
