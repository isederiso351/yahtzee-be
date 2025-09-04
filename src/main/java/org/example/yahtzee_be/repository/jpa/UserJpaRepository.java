package org.example.yahtzee_be.repository.jpa;

import jakarta.persistence.LockModeType;
import org.example.yahtzee_be.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {

    double getCreditByEmail(String email);

    Optional<User> getByEmail(String email);

    boolean existsByKeycloackID(String id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select u from User u where u.keycloackID = :keycloackId")
    Optional<User> getByKeycloackIDLock(@Param("keycloackId")String id);

    Optional<User> getUserById(long id);
}
