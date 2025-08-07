package org.example.yahtzee_be.repository;

import org.example.yahtzee_be.entity.Game;
import org.example.yahtzee_be.entity.User;
import org.example.yahtzee_be.entity.UserGame;
import org.example.yahtzee_be.repository.jpa.UserGameJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserGameRepository {
    @Autowired
    private UserGameJpaRepository userGameJpaRepository;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private UserRepository userRepository;

    public void joinGame(long playerId, long gameId) {
        User user = userRepository.getUser(playerId);
        Game game = gameRepository.getGame(gameId);

        UserGame userGame = new UserGame(user, game);
        userGameJpaRepository.save(userGame);
    }

    public boolean isUserInGame(long userId, long gameId) {
        return userGameJpaRepository.existsByUser_IdAndGame_Id(userId, gameId);
    }

    public int totalCurrentPlayers(long gameId) {
        return userGameJpaRepository.countByGame_Id(gameId);
    }
}
