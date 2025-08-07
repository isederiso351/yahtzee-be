package org.example.yahtzee_be.dto;

import lombok.Builder;
import lombok.Data;
import org.example.yahtzee_be.entity.Game;

import java.util.List;

@Data
public class GameInfoDTO {
    private long gameId;
    private String host;
    private List<String> users;
    private int max_players;
    private double bet;

    public static GameInfoDTO fromEntity(Game game, List<String> users) {
        GameInfoDTO gameInfoDTO = new GameInfoDTO();
        gameInfoDTO.gameId = game.getId();
        gameInfoDTO.host = game.getHost().getName();
        gameInfoDTO.max_players = game.getMaxPlayers();
        gameInfoDTO.bet = game.getBet();
        return gameInfoDTO;
    }
}
