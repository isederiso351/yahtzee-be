package org.example.yahtzee_be.controller;

import org.example.yahtzee_be.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserControllerImpl implements UserController {

    @Autowired
    private UserService userService;

    @Override
    public double getCredit(Jwt jwt){
        return userService.getUserCredit(jwt.getSubject());
    }

}
