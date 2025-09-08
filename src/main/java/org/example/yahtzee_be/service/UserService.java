package org.example.yahtzee_be.service;

import org.example.yahtzee_be.config.GameProperties;
import org.example.yahtzee_be.entity.User;
import org.example.yahtzee_be.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameProperties gameProperties;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public double getUserCredit(String userSub) {
        return userRepository.getUserBySub(userSub).getCredit();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void syncUser(Jwt jwt) {
        userRepository.syncUser(jwt);

        User user = userRepository.getUserBySub(jwt.getSubject());
        checkFirstBonus(user);
        checkDailyBonus(user);
    }

    private void checkFirstBonus(User user) {
        if(!user.isActive()){
            user.setCredit(gameProperties.getFirstAccessCredit());
            user.setActive(true);
        }
    }

    private void checkDailyBonus(User user) {
        if(user.getLastBonusCredit().isBefore(LocalDateTime.now().minusDays(1))){
            user.setCredit(user.getCredit() + gameProperties.getDailyBonus());
        }
    }
}
