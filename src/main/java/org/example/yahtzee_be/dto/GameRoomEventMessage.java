package org.example.yahtzee_be.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameRoomEventMessage {
    private String type; //PLAYER_JOINED, GAME_STARTED, DICE_ROLLED
    private GameInfoDTO game;
    private String playerName; //Event specific
    private String diceResult;
}
