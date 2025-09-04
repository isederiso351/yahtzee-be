package org.example.yahtzee_be.event;

import lombok.Data;
import org.example.yahtzee_be.entity.Game;

@Data
public class PlayerJoinedEvent extends GameEvent {
    private String playerName;
    public PlayerJoinedEvent(Game game, String playerName) {
        super(GameEventType.JOINED, game);
        this.playerName = playerName;
    }
}
