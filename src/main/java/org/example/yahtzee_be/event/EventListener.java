package org.example.yahtzee_be.event;

import org.example.yahtzee_be.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class EventListener {

    @Autowired
    private WebSocketService webSocketService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleGameEvent(GameEvent event) {
        switch (event.getType()) {
            case GameEventType.CREATED -> webSocketService.sendGameCreated(event.getGame());
            case GameEventType.UPDATED -> webSocketService.sendGameUpdated(event.getGame());
            case GameEventType.DELETED -> webSocketService.sendGameDeleted(event.getGame());
            case GameEventType.JOINED -> {
                PlayerJoinedEvent playerJoinedEvent = (PlayerJoinedEvent) event;
                webSocketService.sendPlayerJoined(event.getGame(),playerJoinedEvent.getPlayerName());
            }
            case GameEventType.ROLLED -> {
                RolledDiceEvent rolledDiceEvent = (RolledDiceEvent) event;
                webSocketService.sendRolledDice(event.getGame(), rolledDiceEvent.getPlayerName(), rolledDiceEvent.getDiceResult());
            }
            case GameEventType.STARTED -> {
                webSocketService.sendGameStarted(event.getGame());
            }
        }
    }
}
