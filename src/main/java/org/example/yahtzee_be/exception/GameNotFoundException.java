package org.example.yahtzee_be.exception;

public class GameNotFoundException extends GameException {
    public GameNotFoundException(String message) {
        super(message);
    }
}
