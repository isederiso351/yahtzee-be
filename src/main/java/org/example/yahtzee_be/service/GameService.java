package org.example.yahtzee_be.service;

import org.example.yahtzee_be.config.GameProperties;
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
    public void addGame(int maxPlayers, double bet, String host) {
        //Crei partita e aggiungi utente come player e rimuove credito
        if(maxPlayers > gameProperties.getMaxPlayers()) {
            throw new IllegalArgumentException("Max player count exceeded");
        }

        long gameId = gameRepository.createGame(maxPlayers, host, bet);
        joinGame(gameId, host);

    }

    @Transactional
    public void joinGame(long gameId, String player) {
        userGameRepository.joinGame(gameId, player);

        double bet = gameRepository.getBet(gameId);
        userRepository.removeCredit(player, bet);
    }
}
