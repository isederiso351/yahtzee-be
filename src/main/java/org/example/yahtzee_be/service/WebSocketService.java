package org.example.yahtzee_be.service;

import org.example.yahtzee_be.dto.GameHomeEventMessage;
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

    private void sendGlobalEvent(GameHomeEventMessage message) {
        messagingTemplate.convertAndSend("/topic/games", message);
    }

    private void sendGameSpecificEvent(Long gameId, GameRoomEventMessage message) {
        messagingTemplate.convertAndSend("/topic/game/" + gameId, message);
    }

    public void sendGameCreated(Game game) {
        sendGlobalEvent(new GameHomeEventMessage("CREATED", gameService.toDTO(game), null));
    }

    public void sendGameUpdated(Game game) {
        sendGlobalEvent(new GameHomeEventMessage("UPDATED", gameService.toDTO(game), null));
    }

    public void sendGameDeleted(Game game) {
        sendGlobalEvent(new GameHomeEventMessage("DELETED", null, game.getId()));
    }

    public void sendPlayerJoined(Game game, String playerName) {
        sendGameUpdated(game);
        sendGameSpecificEvent(game.getId(), new GameRoomEventMessage("PLAYER_JOINED", gameService.toDTO(game), playerName, null));
    }

    public void sendRolledDice(Game game, String playerName, String diceResult) {
        sendGameSpecificEvent(game.getId(), new GameRoomEventMessage("DICE_ROLLED", gameService.toDTO(game), playerName, diceResult));
    }

    public void sendGameStarted(Game game) {
        sendGameUpdated(game);
        sendGameSpecificEvent(game.getId(), new GameRoomEventMessage("GAME_STARTED", gameService.toDTO(game), null, null));
    }
}
