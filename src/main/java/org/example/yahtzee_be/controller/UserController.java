package org.example.yahtzee_be.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

public interface UserController {
    @GetMapping("/credit")
    double getCredit(@AuthenticationPrincipal Jwt jwt);



}
