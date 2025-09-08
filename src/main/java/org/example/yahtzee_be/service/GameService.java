package org.example.yahtzee_be.service;

import org.example.yahtzee_be.config.GameProperties;
import org.example.yahtzee_be.dto.GameInfoDTO;
import org.example.yahtzee_be.entity.DiceResult;
import org.example.yahtzee_be.entity.Game;
import org.example.yahtzee_be.entity.User;
import org.example.yahtzee_be.event.GameEventType;
import org.example.yahtzee_be.event.GameEvent;
import org.example.yahtzee_be.model.GameStatus;
import org.example.yahtzee_be.exception.GameException;
import org.example.yahtzee_be.repository.DiceResultRepository;
import org.example.yahtzee_be.repository.GameRepository;
import org.example.yahtzee_be.repository.UserGameRepository;
import org.example.yahtzee_be.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GameService {

    @Autowired
    private GameProperties gameProperties;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private UserGameRepository userGameRepository;

    @Autowired
    private DiceResultRepository diceResultRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Lazy
    @Autowired
    private GameService self;

    private Random random = new Random();

    @Transactional
    public GameInfoDTO createGame(String hostSub, int maxPlayers, double bet) {
        if(maxPlayers > gameProperties.getMaxPlayers()) {
            throw new IllegalArgumentException("Max player count exceeded");
        }
        long hostId = userRepository.getIdBySub(hostSub);
        Game game = gameRepository.createGame(maxPlayers, hostId, bet);

        eventPublisher.publishEvent(new GameEvent(GameEventType.UPDATED, game));

        joinGame(hostSub, game.getId());
        return toDTO(game);
    }

    @Transactional
    public void joinGame(String sub, long gameId) {
        User user = userRepository.getUserBySub(sub);

        if(userGameRepository.isUserInGame(user.getId(), gameId)){
            return;
        }
        Game game = gameRepository.getGameForUpdate(gameId);

        int currPlayers = userGameRepository.totalCurrentPlayers(gameId);
        if(currPlayers >= game.getMaxPlayers()) {
            throw new GameException("Game is full");
        }

        double bet = game.getBet();
        user.setCredit(user.getCredit() - bet);
        System.out.println("rimossi crediti");
        userGameRepository.joinGame(user.getId(), gameId);

        eventPublisher.publishEvent(new GameEvent(GameEventType.JOINED,game));
    }

    @Transactional(readOnly = true)
    public Page<GameInfoDTO> getGames(GameStatus status, Pageable pageable) {
        if(status == null) {
            throw new IllegalArgumentException("Status is null");
        }

        Page<Game> games = gameRepository.getGamesByStatus(status, pageable);
        return games.map(this::toDTO);
    }

    @Transactional
    public void startGame(String userSub, long gameId) {
        Game game = gameRepository.getGameForUpdate(gameId);
        User user = userRepository.getUserBySub(userSub);

        if(!game.getHost().getId().equals(user.getId())) {
            throw new GameException("Only the host can start the game");
        }

        if(game.getStatus() != GameStatus.WAITING) {
            throw new GameException("Game is not in waiting status");
        }

        // Cambia lo status del gioco
        gameRepository.updateGameStatus(gameId, GameStatus.IN_PROGRESS);

        // Inizia il primo tiro di dadi
        self.rollDicesAfterDelay(game, 2000);

        eventPublisher.publishEvent(new GameEvent(GameEventType.STARTED, game));
    }

    @Async
    @Transactional
    public void rollDicesAfterDelay(Game game, long delay) {

        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }

        List<User> playersToRoll;

        // Ottieni il numero del tiro corrente
        int currentRoll = diceResultRepository.getMaxRollNumber(game.getId()) + 1;

        if(currentRoll == 1) {
            // Primo tiro: tutti i giocatori
            playersToRoll = userGameRepository.getPlayers(game.getId());
        } else {
            // Tiri successivi: solo quelli che avevano il punteggio più alto nell'ultimo tiro
            playersToRoll = getPlayersWithHighestScore(game.getId(), currentRoll - 1);
        }

        // Tira i dadi per tutti i giocatori selezionati
        Map<String, Integer> diceResults = new HashMap<>();
        for(User player : playersToRoll) {
            int diceValue = rollDice();
            DiceResult diceResult = new DiceResult(game, player, diceValue, currentRoll);
            diceResultRepository.save(diceResult);
            diceResults.put(player.getName(), diceValue);
        }

        // Invia l'evento con i risultati dei dadi
        eventPublisher.publishEvent(new GameEvent(GameEventType.ROLLED, game));

        self.checkForWinner(game, currentRoll, playersToRoll);
    }

    @Async
    @Transactional
    public void checkForWinner(Game game, int rollNumber, List<User> playersInRoll) {

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
        List<User> winnersThisRoll = getPlayersWithHighestScore(game.getId(), rollNumber);

        if(winnersThisRoll.size() == 1) {
            // Abbiamo un vincitore!
            User winner = winnersThisRoll.getFirst();

            // Completa il gioco
            game = gameRepository.updateGameStatus(game.getId(), GameStatus.COMPLETED);

            // Assegna il premio al vincitore
            double totalPot = game.getBet() * userGameRepository.totalCurrentPlayers(game.getId());
            userRepository.addCredit(winner.getKeycloackID(), totalPot);

            // Invia evento game completed
            eventPublisher.publishEvent(new GameEvent(GameEventType.COMPLETED, game));

        } else {
            self.rollDicesAfterDelay(game, 0); // 3 secondi di pausa tra i tiri
        }
    }

    private List<User> getPlayersWithHighestScore(long gameId, int rollNumber) {
        List<DiceResult> diceResults = diceResultRepository.findByGameIdAndRollNumber(gameId, rollNumber);

        int maxValue = diceResults.stream()
                .mapToInt(DiceResult::getDiceValue)
                .max()
                .orElse(0);

        return diceResults.stream()
                .filter(dr -> dr.getDiceValue() == maxValue)
                .map(DiceResult::getUser)
                .toList();
    }

    private int rollDice(){
        return random.nextInt(6)+1;
    }

    public GameInfoDTO toDTO(Game game) {
        GameInfoDTO dto = new GameInfoDTO();
        dto.setGameId(game.getId());
        dto.setHost(game.getHost().getName());
        dto.setStatus(game.getStatus());
        dto.setBet(game.getBet());
        dto.setMax_players(game.getMaxPlayers());
        dto.setUsers(userGameRepository.getPlayerNames(game.getId()));

        // Se il gioco è in corso o completato, ottieni info sui dadi
        if(game.getStatus() == GameStatus.IN_PROGRESS || game.getStatus() == GameStatus.COMPLETED) {
            int currentRoll = diceResultRepository.getMaxRollNumber(game.getId());
            dto.setCurrentRoll(currentRoll);

            if (currentRoll > 0) {
                // Ottieni i risultati dei dadi dell'ultimo tiro
                List<DiceResult> currentResults = diceResultRepository.findByGameIdAndRollNumber(game.getId(),
                        currentRoll);
                Map<String, Integer> diceResults = currentResults.stream().collect(
                        Collectors.toMap(dr -> dr.getUser().getName(), DiceResult::getDiceValue));
                dto.setCurrentDiceResults(diceResults);


                    List<String> activeUsers = getPlayersWithHighestScore(game.getId(), currentRoll).stream().map(User::getName).toList();
                    dto.setActivePlayers(activeUsers);

                // Se il gioco è completato, trova il vincitore
                if (game.getStatus() == GameStatus.COMPLETED) {
                    if(dto.getActivePlayers().size() != 1) {
                        throw new GameException("Can't get winner");
                    }
                    dto.setWinner(dto.getActivePlayers().getFirst());
                }
            }
        }
        return dto;
    }

    @Transactional(readOnly = true)
    public GameInfoDTO getGame(long gameId) {
        Game game = gameRepository.getGame(gameId);
        return toDTO(game);
    }
}
