package org.example.yahtzee_be.service;

import org.example.yahtzee_be.config.GameProperties;
import org.example.yahtzee_be.exception.GameException;
import org.example.yahtzee_be.repository.GameRepository;
import org.example.yahtzee_be.repository.UserGameRepository;
import org.example.yahtzee_be.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GameService {

    @Autowired
    private GameProperties gameProperties;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private UserGameRepository userGameRepository;

    @Transactional
    public void createGame(String hostSub, int maxPlayers, double bet) {
        if(maxPlayers > gameProperties.getMaxPlayers()) {
            throw new IllegalArgumentException("Max player count exceeded");
        }
        long hostId = userRepository.getIdBySub(hostSub);

        long gameId = gameRepository.createGame(maxPlayers, hostId, bet);
        joinGame(gameId, hostId);

    }

    @Transactional
    public void joinGame(long gameId, long playerId) {
        if(userGameRepository.isUserInGame(playerId, gameId)){
            throw new GameException("User is already in the game");
        }
        int maxPlayers = gameRepository.getMaxPlayers(gameId);
        int currPlayers = userGameRepository.totalCurrentPlayers(gameId);
        if(currPlayers >= maxPlayers) {
            throw new GameException("Game is full");
        }

        double bet = gameRepository.getBet(gameId);
        userRepository.removeCredit(playerId, bet);

        userGameRepository.joinGame(playerId, gameId);
    }
}
