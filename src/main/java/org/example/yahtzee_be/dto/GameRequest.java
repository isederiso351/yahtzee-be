package org.example.yahtzee_be.dto;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class GameRequest {
    @Min(1)
    final int max_players;

    final @Min(0) long bet;
}
