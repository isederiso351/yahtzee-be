package org.example.yahtzee_be.repository.jpa;

import org.example.yahtzee_be.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GameJpaRepository extends JpaRepository<Game, Long> {

    @Query("SELECT g.bet FROM Game g WHERE g.id = :id")
    Double getBetById(@Param("id")Long gameId);
}
