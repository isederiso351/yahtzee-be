package org.example.yahtzee_be.repository.jpa;

import org.example.yahtzee_be.entity.DiceResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiceResultJpaRepository extends JpaRepository<DiceResult, Long> {
    @Query("SELECT COALESCE(MAX(dr.rollNumber), 0) FROM DiceResult dr WHERE dr.game.id = :gameId")
    int getMaxRollNumber(@Param("gameId") Long gameId);

    List<DiceResult> findByGameIdAndRollNumber( Long gameId,int rollNumber);

}
