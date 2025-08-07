package org.example.yahtzee_be.repository;

import org.example.yahtzee_be.entity.Game;
import org.example.yahtzee_be.entity.User;
import org.example.yahtzee_be.entity.UserGame;
import org.example.yahtzee_be.repository.jpa.UserGameJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

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

    public void leaveGame(long userId, long gameId) {
        userGameJpaRepository.removeUserGameByUser_IdAndGame_Id(userId, gameId);
    }

    public List<User> getPlayers(Long gameId) {
        return userGameJpaRepository.getUsersByGameId(gameId);

    }

    public List<String> getPlayerNames(Long id) {
        return getPlayers(id).stream().map(User::getName).toList();
    }
}
