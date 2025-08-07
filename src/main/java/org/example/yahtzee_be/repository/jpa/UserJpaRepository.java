package org.example.yahtzee_be.repository.jpa;

import org.example.yahtzee_be.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {

    double getCreditByEmail(String email);

    Optional<User> getByEmail(String email);

    boolean existsByKeycloackID(String id);

    Optional<User> getByKeycloackID(String id);

    Optional<User> getUserById(long id);
}
