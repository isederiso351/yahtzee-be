package org.example.yahtzee_be.controller;

import jakarta.validation.Valid;
import org.example.yahtzee_be.dto.GameInfoDTO;
import org.example.yahtzee_be.dto.GameRequest;
import org.example.yahtzee_be.model.GameStatus;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;


public interface GameController {

    @GetMapping()
    Page<GameInfoDTO> getGames(@RequestParam GameStatus status,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size);

    @PostMapping("/{gameId}/join")
    void joinGame(@PathVariable long gameId, @AuthenticationPrincipal Jwt jwt);

    @PostMapping("/{gameId}/leave")
    void leaveGame(@PathVariable long gameId, @AuthenticationPrincipal Jwt jwt);


    @PostMapping("/create")
    void createGame(@RequestBody @Valid GameRequest gameRequest, @AuthenticationPrincipal Jwt jwt);
}
