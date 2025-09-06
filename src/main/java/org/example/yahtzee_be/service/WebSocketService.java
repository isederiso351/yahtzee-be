package org.example.yahtzee_be.service;

import org.example.yahtzee_be.dto.GameRoomEventMessage;
import org.example.yahtzee_be.entity.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private GameService gameService;

    public void sendHomeEvent() {
        messagingTemplate.convertAndSend("/topic/games", "UPDATE");
    }

    private void sendRoomEvent(Long gameId, GameRoomEventMessage message) {
        messagingTemplate.convertAndSend("/topic/game/" + gameId, message);
    }

    public void sendPlayerJoined(Game game) {
        sendRoomEvent(game.getId(), new GameRoomEventMessage("PLAYER_JOINED", gameService.toDTO(game)));
    }

    public void sendRolledDice(Game game) {
        sendRoomEvent(game.getId(), new GameRoomEventMessage("DICE_ROLLED", gameService.toDTO(game)));
    }

    public void sendGameStarted(Game game) {
        sendRoomEvent(game.getId(), new GameRoomEventMessage("GAME_STARTED", gameService.toDTO(game)));
    }

    public void sendGameCompleted(Game game) {
        sendRoomEvent(game.getId(), new GameRoomEventMessage("GAME_COMPLETED", gameService.toDTO(game)));
    }
}
