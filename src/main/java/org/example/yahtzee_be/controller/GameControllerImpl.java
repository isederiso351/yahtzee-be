package org.example.yahtzee_be.controller;

import org.example.yahtzee_be.dto.GameInfoDTO;
import org.example.yahtzee_be.dto.GameRequest;
import org.example.yahtzee_be.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void addGame(GameRequest gameRequest) {
        //TODO recuperare host dall'utente autenticato
        gameService.addGame(gameRequest.getMax_players(), gameRequest.getBet(), "Mario");
    }
}
