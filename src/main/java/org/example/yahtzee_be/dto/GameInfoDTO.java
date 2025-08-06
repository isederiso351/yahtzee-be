package org.example.yahtzee_be.dto;

import lombok.Data;

import java.util.List;

@Data
public class GameInfoDTO {
    final long gameId;
    final String host;
    final List<String> users;
    final int max_players;
    final double bet;
}
