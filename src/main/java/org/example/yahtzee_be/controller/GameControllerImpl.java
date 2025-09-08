package org.example.yahtzee_be.controller;

import org.example.yahtzee_be.dto.GameInfoDTO;
import org.example.yahtzee_be.dto.GameRequest;
import org.example.yahtzee_be.model.GameStatus;
import org.example.yahtzee_be.service.GameService;
import org.example.yahtzee_be.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game")
public class GameControllerImpl implements GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private UserService userService;

    @Override
    public Page<GameInfoDTO> getGames(GameStatus status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return gameService.getGames(status, pageable);
    }

    @Override
    public void joinGame(long gameId, Jwt jwt) {
        userService.syncUser(jwt);
        gameService.joinGame(jwt.getSubject(), gameId);
    }

    @Override
    public GameInfoDTO createGame(GameRequest gameRequest, Jwt jwt) {
        userService.syncUser(jwt);
        return gameService.createGame(jwt.getSubject(),gameRequest.getMax_players(), gameRequest.getBet());
    }

    @Override
    public GameInfoDTO getGame(long gameId) {
        return gameService.getGame(gameId);
    }

    @Override
    public void startGame(long gameId, Jwt jwt) {
        userService.syncUser(jwt);
        gameService.startGame(jwt.getSubject(), gameId);
    }
}
