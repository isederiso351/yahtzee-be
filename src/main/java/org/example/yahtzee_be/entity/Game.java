package org.example.yahtzee_be.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Game {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int maxPlayers;

    private double bet;

    @ManyToOne
    @JoinColumn(name = "host")
    private User host;


    public Game(int maxPlayers, User host, double bet) {
        this.maxPlayers = maxPlayers;
        this.bet = bet;
        this.host = host;
    }
}
