package com.jimmccarthy.rugby.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jimmccarthy.rugby.model.entity.Player;

/**
 * The repository for the PLAYERS table.
 */
@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    /**
     * Find all the players with the given jersey number.
     *
     * @param jerseyNo the jersey number
     * @return a list of players
     */
    List<Player> findAllByJerseyNo(int jerseyNo);
}
