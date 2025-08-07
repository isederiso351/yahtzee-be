package org.example.yahtzee_be.repository.jpa;

import org.example.yahtzee_be.entity.User;
import org.example.yahtzee_be.entity.UserGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserGameJpaRepository extends JpaRepository<UserGame, Long> {
    boolean existsByUser_IdAndGame_Id(Long userId, Long gameId);

    int countByGame_Id(Long gameId);

    void removeUserGameByUser_IdAndGame_Id(Long userId, Long gameId);

    List<UserGame> getUserGamesByGame_Id(Long gameId);

    @Query("SELECT ug.user FROM UserGame ug WHERE ug.game.id = :gameId")
    List<User> getUsersByGameId(@Param("gameId") Long gameId);
}
