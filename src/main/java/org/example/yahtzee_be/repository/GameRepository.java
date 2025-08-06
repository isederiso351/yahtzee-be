package org.example.yahtzee_be.repository;

import org.example.yahtzee_be.entity.Game;
import org.example.yahtzee_be.entity.User;
import org.example.yahtzee_be.repository.jpa.GameJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GameRepository {

    @Autowired
    private GameJpaRepository gameJpaRepository;

    @Autowired
    private UserRepository userRepository;


    public long createGame(int maxPlayers, String host, double bet) {
        User userHost = userRepository.getUser(host);
        Game game = new Game(maxPlayers, userHost, bet);
        game = gameJpaRepository.save(game);
        return game.getId();
    }

    public double getBet(long gameId) {
        return gameJpaRepository.getBetById(gameId);
    }
}
