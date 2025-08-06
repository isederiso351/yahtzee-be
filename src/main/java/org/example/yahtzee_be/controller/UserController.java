package org.example.yahtzee_be.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserController {

    //ALla creazione l'utente avrà un credito base e ogni giorno magari aumenta

    @PostMapping("/{email}/register")
    void registerUser(@PathVariable String email, @RequestBody String password);

    @PostMapping("/{email}/login")
    boolean loginUser(@PathVariable String email, @RequestBody String password);

    @PostMapping("/{email}/remove")
    void removeUser(@PathVariable String email);

    @PostMapping("/{email}/update")
    void updateUser(@PathVariable String email, @RequestBody String password);



}
