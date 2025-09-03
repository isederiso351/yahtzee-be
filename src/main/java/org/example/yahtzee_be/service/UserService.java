package org.example.yahtzee_be.service;

import org.example.yahtzee_be.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public double getUserCredit(String userSub) {
        return userRepository.getUserBySub(userSub).getCredit();
    }

    @Transactional
    public void syncUser(String sub, String email, String username) {
        userRepository.syncUser(sub,email,username);
    }
}
