package org.example.yahtzee_be.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GameRequest {
    @Min(1)
    final int max_players;

    @Min(0)
    final double bet;
}
