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
            case GameEventType.UPDATED->{
                webSocketService.sendHomeEvent();
            }
            case GameEventType.JOINED -> {
                webSocketService.sendPlayerJoined(event.getGame());
                webSocketService.sendHomeEvent();
            }
            case GameEventType.ROLLED -> {
                webSocketService.sendRolledDice(event.getGame());
            }
            case GameEventType.STARTED -> {
                webSocketService.sendGameStarted(event.getGame());
                webSocketService.sendHomeEvent();
            }
            case GameEventType.COMPLETED -> {
                webSocketService.sendGameCompleted(event.getGame());
            }
        }
    }
}
