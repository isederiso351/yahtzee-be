package org.example.yahtzee_be.repository;

import org.example.yahtzee_be.entity.Game;
import org.example.yahtzee_be.model.GameStatus;
import org.example.yahtzee_be.entity.User;
import org.example.yahtzee_be.exception.GameNotFoundException;
import org.example.yahtzee_be.repository.jpa.GameJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class GameRepository {

    @Autowired
    private GameJpaRepository gameJpaRepository;

    @Autowired
    private UserRepository userRepository;


    public Game createGame(int maxPlayers, long hostId, double bet) {
        User host = userRepository.getUser(hostId);
        Game game = new Game(maxPlayers, host, bet);
        game = gameJpaRepository.save(game);
        return game;
    }

    public double getBet(long gameId) {
        return gameJpaRepository.getBetById(gameId);
    }

    public Game getGame(long gameId) {
        Optional<Game> game = gameJpaRepository.getGameById(gameId);
        if(game.isEmpty())
            throw new GameNotFoundException("Game not found");
        return game.get();
    }

    public Page<Game> getGamesByStatus(GameStatus status, Pageable pageable) {
        return gameJpaRepository.findByStatus(status, pageable);
    }

    public int getMaxPlayers(long gameId) {
        return getGame(gameId).getMaxPlayers();
    }
}
