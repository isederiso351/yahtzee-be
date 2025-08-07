package org.example.yahtzee_be.repository;

import org.example.yahtzee_be.entity.User;
import org.example.yahtzee_be.exception.UserException;
import org.example.yahtzee_be.exception.UserNotFoundException;
import org.example.yahtzee_be.repository.jpa.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public class UserRepository {

    @Autowired
    private UserJpaRepository userJpaRepository;

    public void removeCredit(long playerId, double bet) {
        User user = getUser(playerId);
        double credit = user.getCredit();
        if(credit < bet)
            throw new UserException("User credit is too low");
        user.setCredit(credit-bet);
        userJpaRepository.save(user);
    }

    public void syncUser(String sub, String email, String username) {
        if (!userJpaRepository.existsByKeycloackID(sub)) {
            User user = new User();
            user.setKeycloackID(sub);
            user.setEmail(email);
            user.setName(username);
            user.setCredit(0);
            userJpaRepository.save(user);
        }
    }

    public long getIdBySub(String hostSub) {
        return getUserBySub(hostSub).getId();
    }

    public User getUser(long id) {
        Optional<User> user = userJpaRepository.getUserById(id);
        if(user.isEmpty())
            throw new UserNotFoundException("User not found");
        return user.get();
    }

    private User getUserBySub(String userSub) {
        Optional<User> user = userJpaRepository.getByKeycloackID(userSub);
        return user.orElseThrow(()->new UserNotFoundException("User not found"));
    }

    public void addCredit(long userId, double bet) {
        User user = getUser(userId);
        double credit = user.getCredit();
        user.setCredit(credit+bet);
        userJpaRepository.save(user);
    }

}
