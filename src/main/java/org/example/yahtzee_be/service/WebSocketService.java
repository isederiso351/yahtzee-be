package org.example.yahtzee_be.service;

import lombok.RequiredArgsConstructor;
import org.example.yahtzee_be.dto.GameEventMessage;
import org.example.yahtzee_be.dto.GameInfoDTO;
import org.example.yahtzee_be.entity.Game;
import org.example.yahtzee_be.event.GameEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private GameService gameService;

    private void sendEvent(GameEventMessage message) {
        messagingTemplate.convertAndSend("/topic/games", message);
    }

    public void sendGameCreated(Game game) {
        sendEvent(new GameEventMessage("CREATED", gameService.toDTO(game), null));
    }

    public void sendGameUpdated(Game game) {
        sendEvent(new GameEventMessage("UPDATED", gameService.toDTO(game), null));
    }

    public void sendGameDeleted(Game game) {
        sendEvent(new GameEventMessage("DELETED", null, game.getId()));
    }
}
