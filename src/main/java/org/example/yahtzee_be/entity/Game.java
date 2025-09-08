package org.example.yahtzee_be.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.yahtzee_be.model.GameStatus;

@Entity
@Data
@NoArgsConstructor
public class Game {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int maxPlayers;

    private long bet;

    @ManyToOne
    @JoinColumn(name = "host")
    private User host;

    @Enumerated(EnumType.STRING)
    private GameStatus status = GameStatus.WAITING;


    public Game(int maxPlayers, User host, long bet) {
        this.maxPlayers = maxPlayers;
        this.bet = bet;
        this.host = host;
    }
}
