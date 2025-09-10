package org.example.yahtzee_be.repository;

import org.example.yahtzee_be.entity.User;
import org.example.yahtzee_be.exception.UserNotFoundException;
import org.example.yahtzee_be.repository.jpa.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;


@Repository
public class UserRepository {

    @Autowired
    private UserJpaRepository userJpaRepository;


    public void syncUser(Jwt jwt) {
        Optional<User> user = userJpaRepository.getByKeycloackIdForUpdate(jwt.getSubject());
        if (user.isPresent()) {
            return;
        }
        User newUser = new User();
        newUser.setKeycloackID(jwt.getSubject());
        newUser.setEmail(jwt.getClaim("email"));
        newUser.setName(jwt.getClaim("preferred_username"));
        newUser.setCredit(500);
        newUser.setLastBonusCredit(LocalDateTime.now());
        userJpaRepository.save(newUser);
    }

    public long getIdBySub(String hostSub) {
        return getUserBySub(hostSub).getId();
    }

    public User getUser(long id) {
        Optional<User> user = userJpaRepository.getUserById(id);
        if (user.isEmpty()) throw new UserNotFoundException("User not found");
        return user.get();
    }

    public User getUserBySub(String sub) {
        Optional<User> user = userJpaRepository.getByKeycloackID(sub);
        return user.orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public User getUserBySubForUpdate(String userSub) {
        Optional<User> user = userJpaRepository.getByKeycloackIdForUpdate(userSub);
        return user.orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public void addCredit(String sub, long amount) {
        User user = getUserBySubForUpdate(sub);
        long credit = user.getCredit();
        user.setCredit(credit + amount);
    }

}
