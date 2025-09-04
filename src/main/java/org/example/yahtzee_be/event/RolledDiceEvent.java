package org.example.yahtzee_be.event;

import lombok.Data;
import org.example.yahtzee_be.entity.Game;

@Data
public class RolledDiceEvent extends GameEvent {
    private String playerName;
    private String diceResult;
    public RolledDiceEvent(Game game, String playerName, String diceResult) {
        super(GameEventType.ROLLED, game);
        this.playerName = playerName;
        this.diceResult = diceResult;
    }
}
