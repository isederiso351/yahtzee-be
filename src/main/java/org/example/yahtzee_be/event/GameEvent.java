package org.example.yahtzee_be.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.yahtzee_be.entity.Game;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameEvent {
    private GameEventType type;
    private Game game;
}

