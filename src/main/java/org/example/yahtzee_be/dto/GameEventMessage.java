package org.example.yahtzee_be.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameEventMessage {
    private String type; // CREATED, UPDATED, DELETED
    private GameInfoDTO game; // per CREATED/UPDATED
    private Long gameId;      // per DELETED
}
