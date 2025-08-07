package org.example.yahtzee_be.service;

import org.example.yahtzee_be.config.GameProperties;
import org.example.yahtzee_be.dto.GameInfoDTO;
import org.example.yahtzee_be.entity.Game;
import org.example.yahtzee_be.model.GameStatus;
import org.example.yahtzee_be.exception.GameException;
import org.example.yahtzee_be.repository.GameRepository;
import org.example.yahtzee_be.repository.UserGameRepository;
import org.example.yahtzee_be.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        joinGame(hostSub, gameId);

    }

    @Transactional
    public void joinGame(String playerSub, long gameId) {
        long playerId = userRepository.getIdBySub(playerSub);

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

    @Transactional
    public void leaveGame(String userSub, long gameId) {
        long userId = userRepository.getIdBySub(userSub);
        if(!userGameRepository.isUserInGame(gameId, userId)) {
            throw new GameException("User is not in the game");
        }

        double bet = gameRepository.getBet(gameId);
        userRepository.addCredit(userId, bet);

        userGameRepository.leaveGame(userId, gameId);
    }

    public Page<GameInfoDTO> getGames(GameStatus status, Pageable pageable) {
        if(status == null) {
            throw new IllegalArgumentException("Status is null");
        }

        Page<Game> games = gameRepository.getGamesByStatus(status, pageable);
        return games.map(game->GameInfoDTO.fromEntity(game,userGameRepository.getPlayerNames(game.getId())));
    }
}
