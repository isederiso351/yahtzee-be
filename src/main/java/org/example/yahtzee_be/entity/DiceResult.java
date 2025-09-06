package org.example.yahtzee_be.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class DiceResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private int diceValue;

    private int rollNumber = 1;

    public DiceResult(Game game, User user, int diceValue, int rollNumber) {
        this.game = game;
        this.user = user;
        this.diceValue = diceValue;
        this.rollNumber = rollNumber;
    }
}