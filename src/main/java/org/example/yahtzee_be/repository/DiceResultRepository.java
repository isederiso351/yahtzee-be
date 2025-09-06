package org.example.yahtzee_be.repository;

import org.example.yahtzee_be.entity.DiceResult;
import org.example.yahtzee_be.repository.jpa.DiceResultJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DiceResultRepository {

    @Autowired
    private DiceResultJpaRepository diceResultJpaRepository;

    public int getMaxRollNumber(Long id) {
        return diceResultJpaRepository.getMaxRollNumber(id);
    }

    public List<DiceResult> findByGameIdAndRollNumber(long gameId, int rollNumber) {
        return diceResultJpaRepository.findByGameIdAndRollNumber(gameId, rollNumber);
    }

    public void save(DiceResult diceResult) {
        diceResultJpaRepository.save(diceResult);
    }
}
