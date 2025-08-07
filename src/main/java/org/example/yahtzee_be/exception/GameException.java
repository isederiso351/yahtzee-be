package org.example.yahtzee_be.exception;

public class GameException extends RuntimeException {
    public GameException(String message) {
        super(message);
    }
}
