package org.example.yahtzee_be.controller;

import jakarta.validation.Valid;
import org.example.yahtzee_be.dto.GameInfoDTO;
import org.example.yahtzee_be.dto.GameRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;


public interface GameController {

    @GetMapping()
    List<GameInfoDTO> getGames(@RequestParam String status);

    @PostMapping("/{gameId}/join")
    void joinGame(@PathVariable long gameId);

    @PostMapping("/{gameId}/leave")
    void leaveGame(@PathVariable long gameId);


    @PostMapping("/add")
    void addGame(@RequestBody @Valid GameRequest gameRequest);
}
