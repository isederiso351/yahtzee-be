package org.example.yahtzee_be.repository.jpa;

import org.example.yahtzee_be.entity.UserGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGameJpaRepository extends JpaRepository<UserGame, Long> {
    boolean existsByUser_IdAndGame_Id(Long userId, Long gameId);

    int countByGame_Id(Long gameId);
}
