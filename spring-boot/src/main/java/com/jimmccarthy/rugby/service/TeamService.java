package com.jimmccarthy.rugby.service;

import com.jimmccarthy.rugby.model.dto.TeamDto;
import com.jimmccarthy.rugby.repository.TeamRepository;
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
@Transactional
@Slf4j
public class TeamService {
    private final TeamRepository teamRepository;
    private final ModelUtil modelUtil;

    @Autowired
    public TeamService(TeamRepository teamRepository, ModelUtil modelUtil) {
        this.teamRepository = teamRepository;
        this.modelUtil = modelUtil;
    }

    /**
     * Get a list of all the teams
     *
     * @return the list of teams
     */
    public List<TeamDto> getTeams() {
        var teams = teamRepository.findAll();
        log.info("Found {} teams", teams.size());

        return teams.stream() //
                .map(modelUtil::toTeamDto) //
                .collect(Collectors.toList());
    }

    /**
     * Find a specific team.
     *
     * @param id the id of the team
     * @return the team
     * @throws AppServiceException    if the {@code id} is not numeric
     * @throws NoSuchElementException if the team could not be found
     */
    public TeamDto getTeam(String id) {
        if (!StringUtils.isNumeric(id)) {
            throw new AppServiceException(String.format("The id %s must be numeric", id));
        }

        var team = teamRepository.findById(Long.parseLong(id)).orElseThrow();
        log.info("Found team {}", team.getName());

        return modelUtil.toTeamDto(team);
    }

    /**
     * Add a new team. Only the team can be added. Players must be added individually to the team.
     *
     * @param dto the team data
     * @return the team that was added
     */
    public TeamDto addTeam(TeamDto dto) {
        if (!dto.getPlayers().isEmpty()) {
            log.error("Can't add players with the team");

            throw new AppServiceException("Players can not be added at the same time as the team.");
        }

        var entity = teamRepository.save(modelUtil.toTeam(dto));
        log.info("Added team {} with ID {}", entity.getName(), entity.getId());

        return modelUtil.toTeamDto(entity);
    }
}
