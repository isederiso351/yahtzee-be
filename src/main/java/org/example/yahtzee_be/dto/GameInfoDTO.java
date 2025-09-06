package org.example.yahtzee_be.dto;

import lombok.Builder;
import lombok.Data;
import org.example.yahtzee_be.entity.Game;
import org.example.yahtzee_be.model.GameStatus;

import java.util.List;
import java.util.Map;

@Data
public class GameInfoDTO {
    private long gameId;
    private String host;
    private GameStatus status;
    private List<String> users;
    private int max_players;
    private double bet;

    private int currentRoll = 0; // Numero del tiro attuale (0 = non iniziato)
    private Map<String, Integer> currentDiceResults; // username -> valore dado ultimo tiro
    private List<String> activePlayers; // giocatori ancora in gara
    private String winner;

}
