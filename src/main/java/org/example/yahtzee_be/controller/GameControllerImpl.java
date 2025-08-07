package org.example.yahtzee_be.controller;

import org.example.yahtzee_be.dto.GameInfoDTO;
import org.example.yahtzee_be.dto.GameRequest;
import org.example.yahtzee_be.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<GameInfoDTO> getGames(String status) {
        //TODO
        return null;
    }

    @Override
    public void joinGame(long gameId) {
        //TODO
    }

    @Override
    public void leaveGame(long gameId) {
        //TODO
    }

    @Override
    public void createGame(GameRequest gameRequest, Jwt jwt) {
        String hostSub = jwt.getSubject();
        gameService.createGame(hostSub,gameRequest.getMax_players(), gameRequest.getBet());
    }
}
