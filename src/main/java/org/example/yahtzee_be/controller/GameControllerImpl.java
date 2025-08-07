package org.example.yahtzee_be.controller;

import org.example.yahtzee_be.dto.GameInfoDTO;
import org.example.yahtzee_be.dto.GameRequest;
import org.example.yahtzee_be.entity.GameStatus;
import org.example.yahtzee_be.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/game")
public class GameControllerImpl implements GameController {

    @Autowired
    private GameService gameService;

    @Override
    public Page<GameInfoDTO> getGames(GameStatus status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return gameService.getGames(status, pageable);
    }

    @Override
    public void joinGame(long gameId, Jwt jwt) {
        gameService.joinGame(jwt.getSubject(), gameId);
    }

    @Override
    public void leaveGame(long gameId, Jwt jwt) {
        gameService.leaveGame(jwt.getSubject(), gameId);
    }

    @Override
    public void createGame(GameRequest gameRequest, Jwt jwt) {
        String hostSub = jwt.getSubject();
        gameService.createGame(hostSub,gameRequest.getMax_players(), gameRequest.getBet());
    }
}
