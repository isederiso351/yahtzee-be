package org.example.yahtzee_be.dto;

import lombok.Builder;
import lombok.Data;
import org.example.yahtzee_be.entity.Game;
import org.example.yahtzee_be.model.GameStatus;

import java.util.List;

@Data
public class GameInfoDTO {
    private long gameId;
    private String host;
    private GameStatus status;
    private List<String> users;
    private int max_players;
    private double bet;

}
