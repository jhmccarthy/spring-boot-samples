package com.jimmccarthy.rugby.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jimmccarthy.rugby.model.entity.Team;

/**
 * The repository for the TEAMS table.
 */
@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    /**
     * Find the team by their name.
     *
     * @param name the team name
     * @return the team
     */
    Optional<Team> findByName(String name);
}
