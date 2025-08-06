package org.example.yahtzee_be.repository;

import org.example.yahtzee_be.entity.User;
import org.example.yahtzee_be.exception.UserException;
import org.example.yahtzee_be.repository.jpa.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public class UserRepository {

    @Autowired
    private UserJpaRepository userJpaRepository;

    public void removeCredit(String userEmail, double bet) {
        User user = getUser(userEmail);
        double credit = user.getCredit();
        if(credit < bet)
            throw new UserException("User credit is too low");
        user.setCredit(credit-bet);
        userJpaRepository.save(user);
    }

    public User getUser(String userEmail) {
        Optional<User> user = userJpaRepository.getByEmail(userEmail);
        if(user.isEmpty())
            throw new UserException("User not found");
        return user.get();
    }
}
